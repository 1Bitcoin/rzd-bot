package ru.telegram.bot.impl.state.search.date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.service.SearchRequestService;
import ru.telegram.bot.impl.service.StateBuilder;
import ru.telegram.bot.impl.state.search.SearchTransition;

@Slf4j
@Component
public class ProcessingDateState implements State {

    private final SearchRequestService searchRequestService;
    private final StateBuilder stateBuilder;

    public ProcessingDateState(SearchRequestService searchRequestService, StateBuilder stateBuilder) {
        this.searchRequestService = searchRequestService;
        this.stateBuilder = stateBuilder;
    }

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.TRUE;
    }

    @Override
    public SendMessage show(Context context) {
        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), SearchTransition.PROCESSING_DATE_STATE.getState());

        String message = context.getMessage();

        // 1. Если введена корректная дата отправления
        if (searchRequestService.checkCorrectDate(message)) {

            // Сохраняем в черновик дату отправления
            context.getDraft().setDate(message);

            log.info("В черновик сохранена дата отправления: {} для пользователя {}", message, context.getChatId());

            // Перевод в следующее состояние
            context.setState(stateBuilder.getBeanStateByClass(SearchTransition.INPUT_TRAIN_STATE.getState()));
            log.info("Состояние {} перевело пользователя {} в следующее состояние {}",
                    SearchTransition.PROCESSING_DIRECTION_STATE.getState(), context.getChatId(), SearchTransition.INPUT_TRAIN_STATE.getState());

        } else {
            // 2. Некорректное направление. Перевод пользователя в предыдущее состояние
            context.setState(stateBuilder.getBeanStateByClass(SearchTransition.INPUT_DIRECTION_STATE.getState()));
            log.info("Состояние {} перевело пользователя {} в предыдущее состояние {}",
                    SearchTransition.PROCESSING_DIRECTION_STATE.getState(), context.getChatId(), SearchTransition.INPUT_DIRECTION_STATE.getState());
        }

        return context.getState().show(context);
    }
}
