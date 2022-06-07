package ru.telegram.bot.impl.bot.context;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.reflection.StateClassBuilder;
import ru.telegram.bot.impl.state.StartState;

/**
 * Контекст пользователя - в каком state он находится.
 */
@Getter
@Setter
public class UserContext {
    private String stateName;

    private String chatId;
    private String message;

    public UserContext(String chatId) {
        this.chatId = chatId;
    }

//    public SendMessage proceed() {
//        return state.nextState(this);
//    }
}
