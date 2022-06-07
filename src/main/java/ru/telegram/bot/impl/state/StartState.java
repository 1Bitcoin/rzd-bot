package ru.telegram.bot.impl.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.bot.context.UserContext;
import ru.telegram.bot.impl.service.KeyboardService;

@Slf4j
@Component
public class StartState implements State {

    @Override
    public SendMessage show(UserContext context) {
        log.info("Пользователь {} находится в {}", context.getChatId(), context.getStateName());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(context.getChatId());
        sendMessage.setText("Вы находитесь в стартовом состоянии");
        sendMessage.setReplyMarkup(KeyboardService.getStartScreen());

        return sendMessage;
    }
}
