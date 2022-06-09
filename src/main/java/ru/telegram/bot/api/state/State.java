package ru.telegram.bot.api.state;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.impl.context.UserContext;

/**
 * Интерфейс описывает состояние, в котором находится пользователь и возможные переходы из него
 */
public interface State {

    /**
     * Ожидает ли текущее состояние получить информацию от пользователя
     * @return true/false
     */
    Boolean isRequiredInformation();

    /**
     * Выводит запрошенную информацию пользователю
     * @param context пользовательский контекст
     * @return сообщение на фронт
     */
    SendMessage show(Context context);
}
