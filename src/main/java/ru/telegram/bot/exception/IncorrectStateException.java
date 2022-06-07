package ru.telegram.bot.exception;

public class IncorrectStateException extends BusinessException {
    public IncorrectStateException(String errorMessage) {
        super(errorMessage);
    }
}
