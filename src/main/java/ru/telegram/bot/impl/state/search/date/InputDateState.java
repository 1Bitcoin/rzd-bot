package ru.telegram.bot.impl.state.search.date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.pl.KeyboardMenu;
import ru.telegram.bot.impl.service.SearchRequestService;
import ru.telegram.bot.impl.service.StateBuilder;
import ru.telegram.bot.impl.state.search.SearchTransition;

@Slf4j
@Component
public class InputDateState implements State {

    private final StateBuilder stateBuilder;

    public InputDateState(StateBuilder stateBuilder) {
        this.stateBuilder = stateBuilder;
    }

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.FALSE;
    }

    @Override
    public SendMessage show(Context context) {
        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), SearchTransition.INPUT_DATE_STATE.getState());

        // 1. Сообщение для пользователя
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(context.getChatId());
        sendMessage.setText("Введите дату отправления. Пример: 20.09.2022");
        sendMessage.setReplyMarkup(KeyboardMenu.getMainMenuKeyboard());

        log.info("Состояние {} сформировало ответ пользователю {}", SearchTransition.INPUT_DATE_STATE.getState(), context.getChatId());

        // 2. Перевод пользователя в состояние обработки даты
        context.setState(stateBuilder.getBeanStateByClass(SearchTransition.PROCESSING_DATE_STATE.getState()));
        log.info("Состояние {} перевело пользователя {} в следующее состояние {}",
                SearchTransition.INPUT_DATE_STATE.getState(), context.getChatId(), SearchTransition.PROCESSING_DATE_STATE.getState());

        return sendMessage;
    }
}
