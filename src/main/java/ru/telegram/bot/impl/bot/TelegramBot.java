package ru.telegram.bot.impl.bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.telegram.bot.impl.facade.MessageResponseFacade;
import ru.telegram.bot.impl.service.RequestParseService;

@Slf4j
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends TelegramLongPollingBot {

    private String name;
    private String token;
    private final MessageResponseFacade messageResponseFacade;
    private final RequestParseService requestParseService;

    public TelegramBot(MessageResponseFacade messageResponseFacade, RequestParseService requestParseService) {
        this.messageResponseFacade = messageResponseFacade;
        this.requestParseService = requestParseService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = messageResponseFacade.handleUpdate(update);
            execute(sendMessage);
            log.info("Отправлен ответ пользователю {} сообщение {}",
                    requestParseService.getChatId(update), requestParseService.getMessage(update));
        } catch (TelegramApiException | NullPointerException e) {
            log.error("Возникло исключение при отправке ответа пользователю {} сообщения {}",
                    requestParseService.getChatId(update), requestParseService.getMessage(update));
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}