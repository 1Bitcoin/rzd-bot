package ru.telegram.bot.impl.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.bot.impl.exception.BusinessException;
import ru.telegram.bot.impl.exception.IncorrectChatIdException;
import ru.telegram.bot.impl.bot.context.UserContext;
import ru.telegram.bot.impl.handler.MessageHandler;

/**
 * Основной сервис обработки запросов от пользователя
 */

@Service
@Getter
@Setter
@Slf4j
public class OrchestrationRequestService {

    private final ContextService contextService;
    private final RequestParseService requestParseService;
    private final MessageHandler messageHandler;

    public OrchestrationRequestService(ContextService contextService, RequestParseService requestParseService,
                                       MessageHandler messageHandler) {

        this.contextService = contextService;
        this.requestParseService = requestParseService;
        this.messageHandler = messageHandler;
    }

    public SendMessage orchestrationRequest(Update update) throws BusinessException {

        // 0. Извлечь данные из полученного запроса
        String chatId = requestParseService.getChatId(update);
        Message message = requestParseService.getMessage(update);

        if (chatId == null) {
            log.error("Параметр chatId равен null, невозможно идентифицировать клиента");
            throw new IncorrectChatIdException("Получено недопустимое значение параметра chatId");
        }

        // 1. Получить контекст пользователя, если он есть, иначе будет сохранен текущий контекст.
        UserContext userContext = contextService.getUserContext(chatId);

        if (userContext == null) {
            userContext = contextService.createUserContext(chatId);
            log.info("Создан контекст для пользователя {}", chatId);
        }

         // 2. Парсинг полученного сообщения
        if (requestParseService.hasCallbackQuery(update)) {

        } else {
            messageHandler.processMessage(message, userContext);
        }

        return userContext.getState().show(userContext);
    }
}
