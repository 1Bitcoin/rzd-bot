package ru.telegram.bot.impl.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.bot.context.StateContext;
import ru.telegram.bot.impl.service.KeyboardService;

@Slf4j
@Component
public class StartState implements State {
    private static final String name = "Start";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SendMessage nextState(StateContext context) {
        log.info("В StartState message: {}", context.getMessage());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(context.getChatId());
        sendMessage.setText(context.getMessage());
        sendMessage.setReplyMarkup(KeyboardService.startScreen);

        context.setState(new MenuState());
        log.info("Переход в {}", context.getState().getName());

        return sendMessage;
    }

    @Override
    public void previousState(StateContext context) {

    }
}
