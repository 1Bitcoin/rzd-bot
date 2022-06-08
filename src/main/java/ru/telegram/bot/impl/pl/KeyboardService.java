package ru.telegram.bot.impl.pl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.telegram.bot.impl.commands.Commands;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardService {
    public static final InlineKeyboardMarkup startScreen = new InlineKeyboardMarkup();

    private static final String SEARCH_LABEL = "Выбрать направление поезда";
    private static final String SUBSCRIPTIONS_LABEL = "Мои подписки";
    private static final String HELP_LABEL = "Помощь";

    static {
//        InlineKeyboardButton inlineKeyboardButtonSearch = new InlineKeyboardButton();
//        inlineKeyboardButtonSearch.setText(SEARCH_LABEL);
//        inlineKeyboardButtonSearch.setCallbackData(Commands.SEARCH.getCommand());
//
//        InlineKeyboardButton inlineKeyboardButtonSubscriptions = new InlineKeyboardButton();
//        inlineKeyboardButtonSubscriptions.setText(SUBSCRIPTIONS_LABEL);
//        inlineKeyboardButtonSubscriptions.setCallbackData(Commands.SUBSCRIPTIONS.getCommand());
//
//        InlineKeyboardButton inlineKeyboardButtonHelp = new InlineKeyboardButton();
//        inlineKeyboardButtonHelp.setText(HELP_LABEL);
//        inlineKeyboardButtonHelp.setCallbackData(Commands.HELP.getCommand());
//
//        List<List<InlineKeyboardButton>> startScreenKeyboardButtons = new ArrayList<>();
//
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        keyboardButtonsRow1.add(inlineKeyboardButtonSearch);
//
//        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
//        keyboardButtonsRow2.add(inlineKeyboardButtonSubscriptions);
//
//        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
//        keyboardButtonsRow3.add(inlineKeyboardButtonHelp);
//
//        startScreenKeyboardButtons.add(keyboardButtonsRow1);
//        startScreenKeyboardButtons.add(keyboardButtonsRow2);
//        startScreenKeyboardButtons.add(keyboardButtonsRow3);
//
//        startScreen.setKeyboard(startScreenKeyboardButtons);
    }

    public static InlineKeyboardMarkup getStartScreen() {
        return startScreen;
    }
}


