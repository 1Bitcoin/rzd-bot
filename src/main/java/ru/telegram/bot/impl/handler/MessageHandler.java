package ru.telegram.bot.impl.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.bot.context.UserContext;
import ru.telegram.bot.impl.exception.IncorrectMessageException;
import ru.telegram.bot.impl.pl.Buttons;
import ru.telegram.bot.impl.reflection.StateClassBuilder;
import ru.telegram.bot.impl.service.ContextService;

/**
 * Сервис обработки текстовых сообщений от пользователя
 */
@Component
@Slf4j
public class MessageHandler {
    private final StateClassBuilder stateClassBuilder;

    public MessageHandler(StateClassBuilder stateClassBuilder) {
        this.stateClassBuilder = stateClassBuilder;
    }

    /**
     * Полученное сообщение - это переход в следующее состояние или сообщение с информацией от пользователя
     * Если это неизвестное сообщение для текущего состояния - переводим пользователя в стартовое состояние
     * @param message сообщение пользователя
     * @param userContext контекст пользователя
     * @throws IncorrectMessageException - в случае, если пришел null в сообщении
     */
    public void processMessage(Message message, UserContext userContext) throws IncorrectMessageException {
        String inputText = message.getText();

        if (inputText == null) {
            log.error("Получено null сообщение");
            throw new IncorrectMessageException("Получено null сообщение");
        }

        // 0. Если полученному сообщению соответствует Enum - это команда, перевод пользователя в другое состояние
        Buttons buttons = Buttons.valueOfMessage(inputText);

        if (buttons != null) {
            userContext.setStateName(buttons.getState());
            log.info("Получено имя state {} из сообщения {}", buttons.getState(), inputText);

            // 1. Получить класс-состояние для текущей команды и сохранить в контекст.
            State state = stateClassBuilder.getBeanStateByClass(stateClassBuilder.getClassByName(buttons.getState()));
            userContext.setState(state);

        } else {
            // 2. Получено текстовое сообщение, нужно определить ожидает ли текущее состояние
            // получения информации от пользователя
            if (userContext.getState() == null) {
                log.warn("Получено сообщение {}, но в контекте пользователя {} не установлено состояние. " +
                        "Будет установлено стартовое состояние без сохранения сообщения", inputText, message.getChatId());
                userContext.setState(stateClassBuilder.getBeanStartState());
            } else {
                // 3. В контексте есть состояние, но нужно проверить ожидает ли оно информацию от пользователя
                if (userContext.getState().isRequiredInformation()) {
                    userContext.setMessage(inputText);
                    log.info("В контекст было сохранено сообщение, полученное от пользователя");
                } else {
                    // 4. Состояние не ожидает получения информации от пользователя, перевод пользователя в стартовое состояние
                    userContext.setState(stateClassBuilder.getBeanStartState());
                    log.info("Текущее состояние не ожидает получения информации от пользователя. " +
                            "Пользователь переведен в стартовое состояние");
                }
            }
        }
    }
}
