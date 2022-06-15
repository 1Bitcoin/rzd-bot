package ru.telegram.bot.impl.context;

import lombok.Getter;
import lombok.Setter;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.api.vo.Draft;
import ru.telegram.bot.impl.vo.SearchDraft;

/**
 * Контекст пользователя - в каком state он находится.
 */
@Getter
@Setter
public class UserContext implements Context {
    // Текущее состояние пользователя
    private State state;

    // Идентификатор пользователя
    private String chatId;

    // Сообщение, полученное от пользователя
    private String message;

    // Информация о поисковом запросе пользователя
    private Draft draft = new SearchDraft();

    public UserContext(String chatId) {
        this.chatId = chatId;
    }

    public void clearContextData() {
        message = null;
        state = null;
        draft = new SearchDraft();
    }

//    public SendMessage proceed() {
//        return state.nextState(this);
//    }
}
