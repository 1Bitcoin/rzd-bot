package ru.telegram.bot.impl.commands;

public enum Commands {
    SEARCH("/search"),
    START("/start"),
    SUBSCRIPTIONS("/subscribe"),
    HELP("/help");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
