package ru.telegram.bot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest
@PropertySource("classpath:application.yaml")
class TelegramBotApplicationTests {

    @Test
    void contextLoads() {
    }

}
