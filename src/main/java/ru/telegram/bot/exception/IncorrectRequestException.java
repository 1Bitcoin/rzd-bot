package ru.telegram.bot.exception;

public class IncorrectRequestException extends BusinessException {
    public IncorrectRequestException(String errorMessage) {
        super(errorMessage);
    }
}
