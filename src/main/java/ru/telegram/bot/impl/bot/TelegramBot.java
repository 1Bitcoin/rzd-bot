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
import ru.telegram.bot.impl.facade.Facade;

@Slf4j
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBot extends TelegramLongPollingBot {

    private String name;
    private String token;
    private final Facade facade;

    public TelegramBot(Facade facade) {
        this.facade = facade;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = facade.handleUpdate(update);
            execute(sendMessage);
            log.info("Отправлен ответ пользователю {} сообщение {}",
                    update.getMessage().getFrom().getUserName(), update.getMessage().getText());
        } catch (TelegramApiException e) {
            log.error("Возникло исключение при отправке ответа пользователю {} сообщения {}",
                    update.getMessage().getFrom().getUserName(), update.getMessage().getText());
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