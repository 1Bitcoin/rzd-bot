package ru.telegram.bot.impl.reflection;

import org.junit.jupiter.api.Test;

class StateClassBuilderTest {
    @Test
    public void getInstanceStateClass() throws ClassNotFoundException {
        String packageName = "ru.telegram.bot.impl.state.";

        System.out.println(Class.forName(packageName + "StartState"));
    }
}