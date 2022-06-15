package ru.telegram.bot.impl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.telegram.bot.api.service.TrainSearchService;
import ru.telegram.bot.impl.vo.Route;
import ru.telegram.bot.impl.vo.Train;
import ru.telegram.bot.impl.vo.TrainStation;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class TrainSearchServiceImpl implements TrainSearchService {

    @Value("${rzd.api.search.station.code}")
    private String stationCodeAPI;

    private final RestTemplate restTemplate = configureInstanceRestTemplate();

    private final ConcurrentHashMap<Integer, String> codeStationCache = new ConcurrentHashMap<>();

    @Override
    public List<Train> getTrainByRouteAndDate(Route route, String date) {
        return null;
    }

    @Override
    public List<TrainStation> getStationByNameStation(String stationName) {
        log.info("URI {}", stationCodeAPI);

        ResponseEntity<TrainStation[]> response =
                restTemplate.getForEntity(
                        stationCodeAPI,
                        TrainStation[].class, stationName);

        log.info("Был отправлен запрос API РЖД для получения кода станции по ее названию. Код ответа: {}", response.getStatusCode());

        TrainStation[] trainStations = response.getBody();

        if (trainStations == null) {
            return Collections.emptyList();
        }

        for (TrainStation station : trainStations) {
            System.out.println(station);

            codeStationCache.put(station.getCode(), station.getName());
        }

        return List.of(trainStations);

    }

    private RestTemplate configureInstanceRestTemplate() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("8.210.83.33", 80));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);

        return new RestTemplate(requestFactory);
    }
}
