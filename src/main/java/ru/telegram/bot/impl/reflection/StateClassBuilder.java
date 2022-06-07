package ru.telegram.bot.impl.reflection;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class StateClassBuilder {

    private static final String packageName = "ru.telegram.bot.impl.state.";

    @SneakyThrows
    public Class<?> getClassByName(String className) {
        return Class.forName(packageName + className);
    }
}
