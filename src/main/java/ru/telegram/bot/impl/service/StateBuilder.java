package ru.telegram.bot.impl.service;

import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.state.search.direction.InputDirectionState;

@Component
public class StateBuilder {

    private final String packageName = "ru.telegram.bot.impl.state.";
    private final String startStateName = "StartState";
    private final ApplicationContext context;

    Class<?> anyType = InputDirectionState.class;

    public StateBuilder(ApplicationContext context) {
        this.context = context;
    }

    @SneakyThrows
    public Class<?> getClassByName(String className) {
        return Class.forName(packageName + className);
    }

    public State getBeanStateByClass(Class<?> clazz) {
        return (State) context.getBean(clazz);
    }

    public State getBeanStartState() {
        return (State) context.getBean(getClassByName(startStateName));
    }

    public State getBeanStateByName(String stateName) {
        return (State) context.getBean(getClassByName(stateName));
    }
}
