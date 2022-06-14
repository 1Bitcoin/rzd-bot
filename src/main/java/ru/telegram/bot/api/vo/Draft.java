package ru.telegram.bot.api.vo;

import ru.telegram.bot.impl.vo.CarType;
import ru.telegram.bot.impl.vo.Route;

import java.util.List;

public interface Draft {

    String getDate();

    Route getRoute();

    List<CarType> getSeats();

    void setDate(String date);

    void setRoute(Route route);

    void setSeats(List<CarType> seats);
}
