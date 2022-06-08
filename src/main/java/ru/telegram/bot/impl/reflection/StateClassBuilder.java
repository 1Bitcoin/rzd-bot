package ru.telegram.bot.impl.reflection;

import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ru.telegram.bot.api.state.State;

@Component
public class StateClassBuilder {

    private final String packageName = "ru.telegram.bot.impl.state.";
    private final String startStateName = "StartState";
    private final ApplicationContext context;

    public StateClassBuilder(ApplicationContext context) {
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
}
