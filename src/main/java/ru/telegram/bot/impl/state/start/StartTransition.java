package ru.telegram.bot.impl.state.start;

import lombok.Getter;

@Getter
public enum StartTransition {
    START_STATE(StartState.class);

    private final Class<?> state;

    StartTransition(Class<?> state) {
        this.state = state;
    }
}
