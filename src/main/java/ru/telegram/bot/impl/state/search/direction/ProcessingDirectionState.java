package ru.telegram.bot.impl.state.search.direction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.service.SearchRequestService;
import ru.telegram.bot.impl.service.StateBuilder;
import ru.telegram.bot.impl.state.search.SearchTransition;

import java.util.List;

@Slf4j
@Component
public class ProcessingDirectionState implements State {

    private final SearchRequestService searchRequestService;
    private final StateBuilder stateBuilder;

    public ProcessingDirectionState(SearchRequestService searchRequestService, StateBuilder stateBuilder) {
        this.searchRequestService = searchRequestService;
        this.stateBuilder = stateBuilder;
    }

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.TRUE;
    }

    @Override
    public SendMessage show(Context context) {
        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), SearchTransition.PROCESSING_DIRECTION_STATE.getState());

        String message = context.getMessage();

        // 1. Если введено корректное направление
        if (searchRequestService.checkCorrectDirection(message)) {
            List<String> directionList = searchRequestService.parseDirection(message);

            // Сохраняем в черновик направление
            context.getDraft().setFrom(directionList.get(0));
            context.getDraft().setTo(directionList.get(1));

            log.info("В черновик сохранено направление: {} для пользователя {}", directionList, context.getChatId());

            // Перевод в следующее состояние
            context.setState(stateBuilder.getBeanStateByClass(SearchTransition.INPUT_DATE_STATE.getState()));
            log.info("Состояние {} перевело пользователя {} в следующее состояние {}",
                    SearchTransition.PROCESSING_DIRECTION_STATE.getState(), context.getChatId(), SearchTransition.INPUT_DATE_STATE.getState());

        } else {
            // 2. Некорректное направление. Перевод пользователя в предыдущее состояние
            context.setState(stateBuilder.getBeanStateByClass(SearchTransition.INPUT_DIRECTION_STATE.getState()));
            log.info("Состояние {} перевело пользователя {} в предыдущее состояние {}",
                    SearchTransition.PROCESSING_DIRECTION_STATE.getState(), context.getChatId(), SearchTransition.INPUT_DIRECTION_STATE.getState());
        }

        return context.getState().show(context);
    }
}
