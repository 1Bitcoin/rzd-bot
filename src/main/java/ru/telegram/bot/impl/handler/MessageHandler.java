package ru.telegram.bot.impl.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.exception.IncorrectMessageException;
import ru.telegram.bot.impl.pl.Buttons;
import ru.telegram.bot.impl.service.StateBuilder;
import ru.telegram.bot.impl.state.start.StartTransition;

/**
 * Сервис обработки текстовых сообщений от пользователя
 */
@Component
@Slf4j
public class MessageHandler {
    private final StateBuilder stateClassBuilder;

    public MessageHandler(StateBuilder stateClassBuilder) {
        this.stateClassBuilder = stateClassBuilder;
    }

    /**
     * Полученное сообщение - это переход в следующее состояние или сообщение с информацией от пользователя
     * Если это неизвестное сообщение для текущего состояния - переводим пользователя в стартовое состояние
     * @param message сообщение пользователя
     * @param userContext контекст пользователя
     * @throws IncorrectMessageException - в случае, если пришел null в сообщении
     */
    public void processMessage(Message message, Context userContext) throws IncorrectMessageException {
        String inputText = message.getText();

        if (inputText == null) {
            log.error("Получено null сообщение");
            throw new IncorrectMessageException("Получено null сообщение");
        }

        // 0. Если полученному сообщению соответствует Enum - это команда, перевод пользователя в другое состояние
        Buttons buttons = Buttons.valueOfMessage(inputText);

        if (buttons != null) {
            log.info("Получено имя state {} из сообщения {}", buttons.getState(), inputText);

            // 1. Получить класс-состояние для текущей команды и сохранить в контекст.
            State state = stateClassBuilder.getBeanStateByClass(buttons.getState());
            userContext.setState(state);

        } else {
            // 2. Получено текстовое сообщение, нужно определить ожидает ли текущее состояние
            // получения информации от пользователя
            if (userContext.getState() == null) {
                log.warn("Получено сообщение {}, но в контекте пользователя {} не установлено состояние. " +
                        "Будет установлено стартовое состояние без сохранения сообщения", inputText, message.getChatId());
                userContext.clearContextData();
                userContext.setState(stateClassBuilder.getBeanStateByClass(StartTransition.START_STATE.getState()));
            } else {
                // 3. В контексте есть состояние, но нужно проверить ожидает ли оно информацию от пользователя
                if (userContext.getState().isRequiredInformation()) {
                    userContext.setMessage(inputText);
                    log.info("В контекст было сохранено сообщение: {}, полученное от пользователя", inputText);
                } else {
                    // 4. Состояние не ожидает получения информации от пользователя, перевод пользователя в стартовое состояние
                    userContext.clearContextData();
                    userContext.setState(stateClassBuilder.getBeanStateByClass(StartTransition.START_STATE.getState()));
                    log.info("Текущее состояние не ожидает получения информации от пользователя. " +
                            "Пользователь переведен в стартовое состояние");
                }
            }
        }
    }
}
