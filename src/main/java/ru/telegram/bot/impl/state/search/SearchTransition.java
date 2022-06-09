package ru.telegram.bot.impl.state.search;

import lombok.Getter;
import ru.telegram.bot.impl.state.search.date.InputDateState;
import ru.telegram.bot.impl.state.search.date.ProcessingDateState;
import ru.telegram.bot.impl.state.search.direction.ProcessingDirectionState;
import ru.telegram.bot.impl.state.search.direction.InputDirectionState;
import ru.telegram.bot.impl.state.search.status.InputTrainState;
import ru.telegram.bot.impl.state.search.status.ProcessingTrainState;

@Getter
public enum SearchTransition {
    SEARCH_STATE(SearchState.class),

    INPUT_DIRECTION_STATE(InputDirectionState.class),
    PROCESSING_DIRECTION_STATE(ProcessingDirectionState.class),

    INPUT_DATE_STATE(InputDateState.class),
    PROCESSING_DATE_STATE(ProcessingDateState.class),

    INPUT_TRAIN_STATE(InputTrainState.class),
    PROCESSING_TRAIN_STATE(ProcessingTrainState.class);

    private final Class<?> state;

    SearchTransition(Class<?> state) {
        this.state = state;
    }
}
