package ru.telegram.bot.impl.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.telegram.bot.impl.bot.TelegramBot;
import ru.telegram.bot.impl.facade.Facade;

@Configuration
public class AppConfig {
    private final TelegramBotConfig telegramBotConfig;

    public AppConfig(TelegramBotConfig telegramBotConfig) {
        this.telegramBotConfig = telegramBotConfig;
    }

    @Bean
    public TelegramBot springBootBot(Facade facade) {
        TelegramBot telegramBot = new TelegramBot(facade);
        telegramBot.setName(telegramBotConfig.getName());
        telegramBot.setToken(telegramBotConfig.getToken());

        return telegramBot;
    }
}
