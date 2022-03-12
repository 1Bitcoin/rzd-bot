package ru.telegram.bot.impl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.telegram.bot.impl.bot.TelegramBot;

@RestController
public class TelegramBotController {

    private final TelegramBot telegramBot;

    public TelegramBotController(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostMapping("/")
    public void onUpdateReceived(@RequestBody Update update) {
        telegramBot.onUpdateReceived(update);
    }

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok().build();
    }
}
