package ru.telegram.bot.impl.state;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.bot.context.StateContext;

@Slf4j
@Component
public class MenuState implements State {
    private static final String name = "Menu";

    @Override
    public String getName() {
        return name;
    }

    @Override
    @SneakyThrows
    public SendMessage nextState(StateContext context) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(context.getChatId());
        sendMessage.setText(context.getMessage());

        String className = context.getMessage();
        context.setState(context.getStateClassBuilder().getInstanceStateClass(className));
        log.info("Переход в {}", context.getState().getName());

        return sendMessage;
    }

    @Override
    public void previousState(StateContext context) {

    }
}
