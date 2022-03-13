package ru.telegram.bot.impl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.telegram.bot.impl.bot.TelegramBot;
import ru.telegram.bot.impl.facade.MessageResponseFacade;
import ru.telegram.bot.impl.service.RequestParseService;

@Configuration
public class ApplicationConfig {
    private final TelegramBotConfig telegramBotConfig;

    public ApplicationConfig(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    @Bean
    public TelegramBot springBootBot(MessageResponseFacade messageResponseFacade,
                                     RequestParseService requestParseService) {
        TelegramBot telegramBot = new TelegramBot(messageResponseFacade, requestParseService);
        telegramBot.setName(telegramBotConfig.getName());
        telegramBot.setToken(telegramBotConfig.getToken());

        return telegramBot;
    }
}
