package ru.telegram.bot.api.state;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.impl.bot.context.UserContext;

public interface State {

    SendMessage show(UserContext context);
}
