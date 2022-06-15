package ru.telegram.bot.impl.state.search.status;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.api.vo.Draft;
import ru.telegram.bot.impl.pl.KeyboardMenu;
import ru.telegram.bot.impl.service.StateBuilder;
import ru.telegram.bot.impl.state.search.SearchTransition;

@Slf4j
@Component
public class InputTrainState implements State {
    private final StateBuilder stateBuilder;

    public InputTrainState(StateBuilder stateBuilder) {
        this.stateBuilder = stateBuilder;
    }

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.FALSE;
    }

    @Override
    public SendMessage show(Context context) {
        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), SearchTransition.INPUT_TRAIN_STATE.getState());

        Draft draft = context.getDraft();

        // 1. Сообщение для пользователя
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(context.getChatId());
        sendMessage.setText(String.format("Выполняется поиск направлений по вашему запросу: От %s до %s. Дата: %s",
                draft.getRoute().getFrom(), draft.getRoute().getTo(), draft.getDate()));
        sendMessage.setReplyMarkup(KeyboardMenu.getMainMenuKeyboard());

        log.info("Состояние {} сформировало ответ пользователю {}", SearchTransition.INPUT_TRAIN_STATE.getState(), context.getChatId());

        // 2. Перевод пользователя в состояние выбора типа места
        context.setState(stateBuilder.getBeanStateByClass(SearchTransition.PROCESSING_TRAIN_STATE.getState()));
        log.info("Состояние {} перевело пользователя {} в следующее состояние {}",
                SearchTransition.INPUT_TRAIN_STATE.getState(), context.getChatId(), SearchTransition.PROCESSING_TRAIN_STATE.getState());

        return sendMessage;
    }
}
