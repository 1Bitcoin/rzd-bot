package ru.telegram.bot.impl.exception;

public class BusinessException extends Exception {
    public BusinessException(String errorMessage) {
        super(errorMessage);
    }
}
