package ru.telegram.bot.api.context;

import ru.telegram.bot.api.state.State;
import ru.telegram.bot.api.vo.Draft;

/**
 * Интерфейс описывает контракт для всех производных контекстов
 */
public interface Context {

    String getChatId();

    String getMessage();

    State getState();

    void setChatId(String chatId);

    void setMessage(String message);

    void setState(State state);

    void clearContextData();

    Draft getDraft();
}
