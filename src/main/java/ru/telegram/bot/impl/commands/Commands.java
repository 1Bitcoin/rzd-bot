package ru.telegram.bot.impl.commands;

public enum Commands {
    SEARCH("SearchState"),
    START("StartState"),
    SUBSCRIPTIONS("SubscriptionsState"),
    HELP("HelpState");

    private String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
