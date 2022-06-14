package ru.telegram.bot.impl.state.search.direction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.pl.KeyboardMenu;
import ru.telegram.bot.impl.service.StateBuilder;
import ru.telegram.bot.impl.state.search.SearchTransition;

@Slf4j
@Component
public class InputRouteState implements State {

    private final StateBuilder stateBuilder;

    public InputRouteState(StateBuilder stateBuilder) {
        this.stateBuilder = stateBuilder;
    }

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.FALSE;
    }

    @Override
    public SendMessage show(Context context) {
        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), SearchTransition.INPUT_ROUTE_STATE.getState());

        // 1. Сообщение для пользователя
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(context.getChatId());
        sendMessage.setText("Введите направление. Пример: Москва-Саратов");
        sendMessage.setReplyMarkup(KeyboardMenu.getMainMenuKeyboard());

        log.info("Состояние {} сформировало ответ пользователю {}", SearchTransition.INPUT_ROUTE_STATE.getState(), context.getChatId());

        // 2. Перевод пользователя в состояние обработки направления
        context.setState(stateBuilder.getBeanStateByClass(SearchTransition.PROCESSING_ROUTE_STATE.getState()));
        log.info("Состояние {} перевело пользователя {} в следующее состояние {}",
                SearchTransition.INPUT_ROUTE_STATE.getState(), context.getChatId(), SearchTransition.PROCESSING_ROUTE_STATE.getState());

        return sendMessage;
    }
}
