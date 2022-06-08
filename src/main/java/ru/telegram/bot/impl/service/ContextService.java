package ru.telegram.bot.impl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.telegram.bot.impl.bot.context.UserContext;
import ru.telegram.bot.impl.reflection.StateClassBuilder;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Сервис для определения контекста пользователя.
 * Контексты хранятся в мапе.
 */
@Slf4j
@Service
public class ContextService {
    private final ConcurrentHashMap<String, UserContext> cacheContext = new ConcurrentHashMap<>();

    /**
     * Получить контекст пользователя
     * @param chatId идентификатор чата
     * @return контекст пользователя
     */
    public UserContext getUserContext(String chatId) {
        return cacheContext.get(chatId);
    }

    public void updateUserContext(UserContext userContext, String message) {
        userContext.setMessage(message);
    }

    public UserContext createUserContext(String chatId) {
        var userContext = new UserContext(chatId);
        cacheContext.put(chatId, userContext);

        return userContext;
    }
}

