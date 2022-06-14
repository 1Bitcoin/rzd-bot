package ru.telegram.bot.impl.vo;

import lombok.Data;
import ru.telegram.bot.api.vo.Draft;

import java.util.List;

@Data
public class SearchDraft implements Draft {
    // Дата отправления
    private String date;

    // Маршрут
    private Route route;

    // Интересующие места
    private List<CarType> seats;
}
