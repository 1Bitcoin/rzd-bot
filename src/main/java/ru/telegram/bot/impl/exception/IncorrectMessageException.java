package ru.telegram.bot.impl.exception;

public class IncorrectMessageException extends BusinessException {
    public IncorrectMessageException(String errorMessage) {
        super(errorMessage);
    }
}
