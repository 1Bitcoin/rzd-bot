package ru.telegram.bot.impl.state.search.status;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.telegram.bot.api.context.Context;
import ru.telegram.bot.api.state.State;
import ru.telegram.bot.impl.pl.KeyboardMenu;
import ru.telegram.bot.impl.service.SearchRequestService;
import ru.telegram.bot.impl.service.StateBuilder;
import ru.telegram.bot.impl.state.search.SearchTransition;

import java.util.List;

@Slf4j
@Component
public class ProcessingTrainState implements State {

    private final SearchRequestService searchRequestService;
    private final StateBuilder stateBuilder;

    public ProcessingTrainState(SearchRequestService searchRequestService, StateBuilder stateBuilder) {
        this.searchRequestService = searchRequestService;
        this.stateBuilder = stateBuilder;
    }

    @Override
    public Boolean isRequiredInformation() {
        return Boolean.TRUE;
    }

    @Override
    public SendMessage show(Context context) {
        log.info("Пользователь {} находится в состоянии {}", context.getChatId(), SearchTransition.PROCESSING_TRAIN_STATE.getState());

        // Stub
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(context.getChatId());
        sendMessage.setText("Функционал подписок в разработке");
        sendMessage.setReplyMarkup(KeyboardMenu.getMainMenuKeyboard());

        // 1. Сохраняем в черновик данные о типе места

        // 2.
        context.setState(stateBuilder.getBeanStateByClass(SearchTransition.SEARCH_STATE.getState()));
        log.info("Состояние {} перевело пользователя {} в следующее состояние {}",
                SearchTransition.PROCESSING_TRAIN_STATE.getState(), context.getChatId(), SearchTransition.SEARCH_STATE.getState());


        return sendMessage;
    }
}
