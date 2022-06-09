package ru.telegram.bot.impl.state.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.context.UserContext;
import ru.telegram.bot.impl.pl.KeyboardMenu;

@Slf4j
@Component
public class SubscribeState implements State {

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.FALSE;
    }

    @Override
    public SendMessage show(Context context) {
        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), context.getState().toString());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(context.getChatId());
        sendMessage.setText("Вы нажали на ПОДПИСКИ");
        sendMessage.setReplyMarkup(KeyboardMenu.getMainMenuKeyboard());

        return sendMessage;
    }
}
