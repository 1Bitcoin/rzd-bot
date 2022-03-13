package ru.telegram.bot.impl.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class RequestParseService {
    public String getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId().toString();
        }

        return null;
    }

    public String getMessage(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getText();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData();
        }

        return null;
    }
}
