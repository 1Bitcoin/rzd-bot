package ru.telegram.bot.impl.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.bot.impl.exception.BusinessException;
import ru.telegram.bot.impl.handler.MessageHandler;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrchestrationRequestServiceTest {

    @InjectMocks
    public OrchestrationRequestService orchestrationRequestService;

    @Mock
    public ContextService contextService;

    @Mock
    public RequestParseService requestParseService;

    @Mock
    public MessageHandler messageHandler;

    @Mock
    public Update update = Mockito.mock(Update.class);

    @Mock
    public Message message = Mockito.mock(Message.class);

    @Test
    @DisplayName("Передан chatId равный null")
    void orchestrationRequest() {
        //given

        // when
        when(requestParseService.getChatId(update)).thenReturn(null);

        // then
        assertThrows(BusinessException.class, () -> orchestrationRequestService.orchestrationRequest(update));
    }
}