package ru.telegram.bot.impl.reflection;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.telegram.bot.api.state.State;

@Component
public class StateClassBuilder {

    private static final String packageName = "ru.telegram.bot.impl.state.";

    @SneakyThrows
    public State getInstanceStateClass(String className) {
        return (State) Class.forName(packageName + className).getConstructor().newInstance();
    }
}
