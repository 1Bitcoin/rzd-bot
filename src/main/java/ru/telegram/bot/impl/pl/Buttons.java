package ru.telegram.bot.impl.pl;

import lombok.Getter;

@Getter
public enum Buttons {
    SEARCH_BUTTON("Выбрать направление поезда", "SearchState"),
    SUBSCRIBE_BUTTON("Мои подписки", "SubscribeState"),
    HELP_BUTTON("Помощь", "HelpState"),
    START("/start", "StartState");

    private final String message;
    private final String state;

    Buttons(String message, String state) {
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
