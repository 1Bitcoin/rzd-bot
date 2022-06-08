package ru.telegram.bot.impl.commands;

import lombok.Getter;

@Getter
public enum Commands {
    SEARCH_COMMAND("Выбрать направление поезда", "SearchState"),
    SUBSCRIBE_COMMAND("Мои подписки", "SubscribeState"),
    HELP_COMMAND("Помощь", "HelpState"),
    START_COMMAND("/start", "StartState");

    private final String command;
    private final String state;

    Commands(String command, String state) {
        this.command = command;
        this.state = state;
    }
}
