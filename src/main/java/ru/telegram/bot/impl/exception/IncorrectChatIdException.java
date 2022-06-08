package ru.telegram.bot.impl.exception;

public class IncorrectChatIdException extends BusinessException {
    public IncorrectChatIdException(String errorMessage) {
        super(errorMessage);
    }
}
