package ru.telegram.bot.impl.service;

import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.state.search.direction.InputRouteState;

@Component
public class StateBuilder {

    private final static String packageName = "ru.telegram.bot.impl.state.";
    private final static String startStateName = "StartState";
    private final ApplicationContext context;

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
