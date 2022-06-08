package ru.telegram.bot.impl.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.telegram.bot.exception.BusinessException;
import ru.telegram.bot.impl.service.ContextService;
import ru.telegram.bot.impl.service.OrchestrationRequestService;
import ru.telegram.bot.impl.service.RequestParseService;

/**
 * Реализация API Telegram бота для получения запросов пользователя.
 */

@Slf4j
@Getter
@Setter
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String name;

    @Value("${telegram.bot.token}")
    private String token;

    private final OrchestrationRequestService orchestrationRequestService;

    public TelegramBot(OrchestrationRequestService orchestrationRequestService) {
        this.orchestrationRequestService = orchestrationRequestService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = orchestrationRequestService.orchestrationRequest(update);
            execute(sendMessage);
            log.info("Отправлен ответ пользователю {} сообщение {}", sendMessage.getChatId(), sendMessage.getText());

        } catch (BusinessException e) {
            // сделать обертку - сервис на тех перерыве
            throw new RuntimeException(e);
        } catch (TelegramApiException | NullPointerException e) {
            log.error("Возникло исключение при отправке пользователю {} сообщения {}");
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