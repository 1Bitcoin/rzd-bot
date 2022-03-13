package ru.telegram.bot.impl.facade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.bot.impl.bot.context.StateContext;
import ru.telegram.bot.impl.reflection.StateClassBuilder;
import ru.telegram.bot.impl.service.KeyboardService;
import ru.telegram.bot.impl.service.RequestParseService;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class MessageResponseFacade {

    private final ConcurrentHashMap<String, StateContext> cacheContext = new ConcurrentHashMap<>();
    private final RequestParseService requestParseService;
    private final StateClassBuilder stateClassBuilder;

    public MessageResponseFacade(RequestParseService requestParseService, StateClassBuilder stateClassBuilder) {
        this.requestParseService = requestParseService;
        this.stateClassBuilder = stateClassBuilder;
    }

    public SendMessage handleUpdate(Update update) {
        String chatId = requestParseService.getChatId(update);
        String message = requestParseService.getMessage(update);
        StateContext stateContext = getContext(chatId);

        stateContext.setMessage(message);

        SendMessage sendMessage = stateContext.proceed();

        return sendMessage;
    }

    private StateContext getContext(String chatId) {
        try {
            StateContext stateContext = cacheContext.get(chatId);

            if (stateContext == null) {
                cacheContext.put(chatId, new StateContext(stateClassBuilder, chatId));
                return cacheContext.get(chatId);
            } else {
                return stateContext;
            }

        } catch (NullPointerException nullPointerException) {
            log.error("Возникло исключение при получении контекста пользователя с chatId = {}", chatId);
            log.error(nullPointerException.getMessage());
            nullPointerException.printStackTrace();
        }

        return null;
    }

    private SendMessage processMessage(Update update) {
        String message = update.getMessage().getText().trim();
        String chatId = update.getMessage().getChatId().toString();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(KeyboardService.startScreen);

        return sendMessage;
    }

    private SendMessage processCommand(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        String message = callbackQuery.getMessage().getText().trim();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        return new SendMessage();
    }
}

