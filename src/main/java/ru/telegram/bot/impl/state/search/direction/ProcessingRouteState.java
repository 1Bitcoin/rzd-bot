package ru.telegram.bot.impl.state.search.direction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.service.TrainSearchService;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.service.SearchRequestService;
import ru.telegram.bot.impl.service.StateBuilder;
import ru.telegram.bot.impl.state.search.SearchTransition;
import ru.telegram.bot.impl.vo.Route;
import ru.telegram.bot.impl.vo.TrainStation;

import java.util.List;

@Slf4j
@Component
public class ProcessingRouteState implements State {

    private final SearchRequestService searchRequestService;
    private final StateBuilder stateBuilder;
    private final TrainSearchService trainSearchService;

    public ProcessingRouteState(SearchRequestService searchRequestService, StateBuilder stateBuilder, TrainSearchService trainSearchService) {
        this.searchRequestService = searchRequestService;
        this.stateBuilder = stateBuilder;
        this.trainSearchService = trainSearchService;
    }

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.TRUE;
    }

    @Override
    public SendMessage show(Context context) {
        var draft = context.getDraft();

        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), SearchTransition.PROCESSING_ROUTE_STATE.getState());

        String message = context.getMessage();

        // 1. Некорректное направление. Перевод пользователя в предыдущее состояние
        if (!searchRequestService.checkCorrectDirection(message)) {
            return toPreviousState(context);
        }

        List<String> directionList = searchRequestService.parseDirection(message);

        // 2. Сохраняем в черновик направление
        draft.setRoute(new Route(directionList.get(0), directionList.get(1)));
        log.info("В черновик сохранено направление: {} для пользователя {}", directionList, context.getChatId());

        // 3. Поиск кода станции
        List<TrainStation> codeStationsFrom = trainSearchService.getStationByNameStation(draft.getRoute().getFrom());
        List<TrainStation> codeStationsTo = trainSearchService.getStationByNameStation(draft.getRoute().getTo());

        // 4. Коды для введенных станций не найдены
        // TODO обработка ошибки получения ответа от РЖД
        if (codeStationsFrom.isEmpty() || codeStationsTo.isEmpty()) {
            return toPreviousState(context);
        }

        // Перевод в следующее состояние
        context.setState(stateBuilder.getBeanStateByClass(SearchTransition.INPUT_DATE_STATE.getState()));
        log.info("Состояние {} перевело пользователя {} в следующее состояние {}",
                SearchTransition.PROCESSING_ROUTE_STATE.getState(), context.getChatId(), SearchTransition.INPUT_DATE_STATE.getState());


        return context.getState().show(context);
    }

    private SendMessage toPreviousState(Context context) {
        context.setState(stateBuilder.getBeanStateByClass(SearchTransition.INPUT_ROUTE_STATE.getState()));
        log.info("Состояние {} перевело пользователя {} в предыдущее состояние {}",
                SearchTransition.PROCESSING_ROUTE_STATE.getState(), context.getChatId(), SearchTransition.INPUT_ROUTE_STATE.getState());

        return context.getState().show(context);
    }
}
