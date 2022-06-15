package ru.telegram.bot.api.service;

import ru.telegram.bot.impl.vo.Route;
import ru.telegram.bot.impl.vo.Train;
import ru.telegram.bot.impl.vo.TrainStation;

import java.util.List;

/**
 * Сервис для работы с API РЖД
 * Поиск поездов по заданной дате и направлению
 * Поиск кода станции по ее названию
 */
public interface TrainSearchService {

    List<Train> getTrainByRouteAndDate(Route route, String date);

    List<TrainStation> getStationByNameStation(String nameStation);

}
