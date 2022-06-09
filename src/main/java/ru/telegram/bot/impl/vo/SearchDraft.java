package ru.telegram.bot.impl.vo;

import lombok.Getter;
import lombok.Setter;
import ru.telegram.bot.api.vo.Draft;

import java.util.List;

@Getter
@Setter
public class SearchDraft implements Draft {
    // Дата отправления
    private String date;

    // Место отправления
    private String from;

    // Место назначения
    private String to;

    // Интересующие места
    private List<CarType> seats;
}
