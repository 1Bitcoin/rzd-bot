package ru.telegram.bot.impl.bot.context;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.reflection.StateClassBuilder;
import ru.telegram.bot.impl.state.StartState;

@Getter
@Setter
public class StateContext {
    private State state = new StartState();
    private final StateClassBuilder stateClassBuilder;

    private final String chatId;
    private String message;

    public StateContext(StateClassBuilder stateClassBuilder, String chatId) {
        this.stateClassBuilder = stateClassBuilder;
        this.chatId = chatId;
    }

    public SendMessage proceed() {
        return state.nextState(this);
    }
}
