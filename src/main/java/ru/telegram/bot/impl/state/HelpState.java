package ru.telegram.bot.impl.state;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.bot.context.StateContext;

@Component
public class HelpState implements State {

    private static final String name = "Help";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SendMessage nextState(StateContext context) {
        return null;
    }

    @Override
    public void previousState(StateContext context) {

    }
}
