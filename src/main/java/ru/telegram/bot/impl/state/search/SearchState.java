package ru.telegram.bot.impl.state.search;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.service.StateBuilder;

@Slf4j
@Component
public class SearchState implements State {

    private final StateBuilder stateBuilder;

    public SearchState(StateBuilder stateBuilder) {
        this.stateBuilder = stateBuilder;
    }

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.FALSE;
    }

    @Override
    public SendMessage show(Context context) {
        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), SearchTransition.SEARCH_STATE.getState());

        // 1. Перевод пользователя в состояние получения направления
        context.setState(stateBuilder.getBeanStateByClass(SearchTransition.INPUT_ROUTE_STATE.getState()));
        log.info("Состояние {} перевело пользователя {} в следующее состояние {}",
                SearchTransition.SEARCH_STATE.getState(), context.getChatId(), SearchTransition.INPUT_ROUTE_STATE.getState());

        return context.getState().show(context);
    }
}
