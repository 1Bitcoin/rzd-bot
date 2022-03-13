package ru.telegram.bot.api.state;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.impl.bot.context.StateContext;

public interface State {
            
    String getName();

    SendMessage nextState(StateContext context);

    void previousState(StateContext context);
}
