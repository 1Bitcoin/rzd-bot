package ru.telegram.bot.impl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.telegram.bot.impl.context.UserContext;

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

