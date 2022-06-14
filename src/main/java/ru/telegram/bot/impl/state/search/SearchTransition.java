package ru.telegram.bot.impl.state.search;

import lombok.Getter;
import ru.telegram.bot.impl.state.search.date.InputDateState;
import ru.telegram.bot.impl.state.search.date.ProcessingDateState;
import ru.telegram.bot.impl.state.search.direction.ProcessingRouteState;
import ru.telegram.bot.impl.state.search.direction.InputRouteState;
import ru.telegram.bot.impl.state.search.status.InputTrainState;
import ru.telegram.bot.impl.state.search.status.ProcessingTrainState;

@Getter
public enum SearchTransition {
    SEARCH_STATE(SearchState.class),

    INPUT_ROUTE_STATE(InputRouteState.class),
    PROCESSING_ROUTE_STATE(ProcessingRouteState.class),

    INPUT_DATE_STATE(InputDateState.class),
    PROCESSING_DATE_STATE(ProcessingDateState.class),

    INPUT_TRAIN_STATE(InputTrainState.class),
    PROCESSING_TRAIN_STATE(ProcessingTrainState.class);

    private final Class<?> state;

    SearchTransition(Class<?> state) {
        this.state = state;
    }
}
