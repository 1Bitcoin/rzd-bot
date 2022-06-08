package ru.telegram.bot.impl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.bot.impl.exception.IncorrectStateException;

import java.util.Optional;

@Service
@Slf4j
public class RequestParseService {
    public String getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId().toString();
        }

        return null;
    }

    public Message getMessage(Update update) {
        return update.getMessage();
    }

    /**
     *  Проверка введена ли команда пользователем в ручную или отправлена за счет нажатия кнопки
     * @param update сущность с информацией от пользователя
     * @return признак была ли введена команда
     */
    public Boolean isCommand(Update update) {
        return update.hasMessage() && update.getMessage().hasEntities() || update.hasCallbackQuery();
    }

    public Boolean hasCallbackQuery(Update update) {
        return update.hasCallbackQuery();
    }

    /**
     * Преобразовывает команду вида /start к StartState, /menu к MenuState
     * Обрабатываются как команды от ввода пользователя, так и  нажатия кнопок в интерфейсе
     * @return название state класса, например StartState
     */
    public String getStateName(Update update) throws IncorrectStateException {
        if (update.hasMessage() && update.getMessage().hasEntities()) {
            Optional<MessageEntity> messageEntityOptional = update.getMessage().getEntities().stream()
                    .filter(messageEntity -> "bot_command".equals(messageEntity.getType()))
                    .findFirst();

            if (messageEntityOptional.isPresent()) {
                String command = update.getMessage().getText().substring(messageEntityOptional.get().getOffset(), messageEntityOptional.get().getLength());
                return convertCommandToStateName(command);

            } else {
                log.error("Передана неподдерживаемая команда, установлено стартовое состояние");
                throw new IncorrectStateException("Передана неподдерживаемая команда");
            }
        } else if (update.hasCallbackQuery()) {
            String command = update.getCallbackQuery().getData();
            log.info("Получена команда от кнопки {}", command);
            return convertCommandToStateName(command);

        } else {
            log.error("Передана неподдерживаемая команда, установлено стартовое состояние");
            throw new IncorrectStateException("Передана неподдерживаемая команда");
        }
    }

    private String convertCommandToStateName(String command) {
        StringBuilder stringBuilder = new StringBuilder(command);
        stringBuilder.deleteCharAt(0);
        stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
        stringBuilder.append("State");

        return stringBuilder.toString();
    }

    public Boolean hasTextMessage(Update update) {
        return update.getMessage().hasText();
    }
}
