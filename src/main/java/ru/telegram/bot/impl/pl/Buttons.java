package ru.telegram.bot.impl.pl;

import lombok.Getter;
import ru.telegram.bot.impl.state.help.HelpState;
import ru.telegram.bot.impl.state.search.SearchState;
import ru.telegram.bot.impl.state.start.StartState;
import ru.telegram.bot.impl.state.subscribe.SubscribeState;

@Getter
public enum Buttons {
    SEARCH_BUTTON("Выбрать направление поезда", SearchState.class),
    SUBSCRIBE_BUTTON("Мои подписки", SubscribeState.class),
    HELP_BUTTON("Помощь", HelpState.class),
    START("/start", StartState.class);

    private final String message;
    private final Class<?> state;

    Buttons(String message, Class<?> state) {
        this.message = message;
        this.state = state;
    }

    public static Buttons valueOfMessage(String input) {
        for (Buttons buttons : values()) {
            if (buttons.message.equals(input)) {
                return buttons;
            }
        }
        return null;
    }
}
