package ru.telegram.bot.impl.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.exception.BusinessException;
import ru.telegram.bot.exception.IncorrectRequestException;
import ru.telegram.bot.impl.bot.context.UserContext;
import ru.telegram.bot.impl.reflection.StateClassBuilder;

/**
 * Основной сервис обработки запросов от пользователя
 */

@Service
@Getter
@Setter
@Slf4j
public class OrchestrationRequestService {

    private final ContextService contextService;
    private final RequestParseService requestParseService;
    private final ApplicationContext context;
    private final StateClassBuilder stateClassBuilder;

    public OrchestrationRequestService(ContextService contextService, RequestParseService requestParseService,
                                       ApplicationContext context, StateClassBuilder stateClassBuilder) {

        this.contextService = contextService;
        this.requestParseService = requestParseService;
        this.context = context;
        this.stateClassBuilder = stateClassBuilder;
    }

    public SendMessage orchestrationRequest(Update update) throws BusinessException {

        // 0. Извлечь данные из полученного запроса
        String chatId = requestParseService.getChatId(update);
        String message = requestParseService.getMessage(update);

        if (chatId == null) {
            log.error("Параметр chatId равен null, невозможно идентифицировать клиента");
            throw new IncorrectRequestException("Получено недопустимое значение параметра chatId");
        }


        // 1. Получить контекст пользователя, если он есть,иначе будет сохранен текущий контекст.
        UserContext userContext = contextService.getUserContext(chatId);

        if (userContext == null) {
            userContext = contextService.setUserContext(chatId);
            log.info("Создан контекст для пользователя {}", chatId);
        }

         // 2. Парсинг полученного сообщения - это команда или текст.
        if (requestParseService.isCommand(update)) {
            String stateName = requestParseService.getStateName(update);

            userContext.setStateName(stateName);
            log.info("Получено имя state {} из команды {}", stateName, message);

        } else if (requestParseService.hasTextMessage(update)) {
            contextService.updateUserContext(userContext, requestParseService.getMessage(update));
            log.info("Получено сообщение {}", message);
        }


         // 3. Получить класс-состояние для текущей команды и отрисовать.
        Class<?> clazz = stateClassBuilder.getClassByName(userContext.getStateName());
        State state = (State) context.getBean(clazz);

        return state.show(userContext);
    }
}
