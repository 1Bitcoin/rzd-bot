package ru.telegram.bot.impl.exception;

public class IncorrectStateException extends BusinessException {
    public IncorrectStateException(String errorMessage) {
        super(errorMessage);
    }
}
