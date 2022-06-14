package ru.telegram.bot.impl.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
        // Сделать на стримах
        List<String> list = Arrays.asList((direction.split("-")));
        list.replaceAll(String::toUpperCase);
        return list;
    }
}
