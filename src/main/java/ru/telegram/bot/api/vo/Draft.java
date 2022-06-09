package ru.telegram.bot.api.vo;

import ru.telegram.bot.impl.vo.CarType;

import java.util.List;

public interface Draft {

    String getDate();

    String getFrom();

    String getTo();

    List<CarType> getSeats();

    void setDate(String date);

    void setFrom(String from);

    void setTo(String to);

    void setSeats(List<CarType> seats);
}
