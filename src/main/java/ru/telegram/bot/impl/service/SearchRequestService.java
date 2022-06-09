package ru.telegram.bot.impl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SearchRequestService {
    public boolean checkCorrectDirection(String direction) {
        return true;
    }

    public boolean checkCorrectDate(String date) {
        return true;
    }

    public List<String> parseDirection(String direction) {
        return List.of(direction.split("-"));
    }
}
