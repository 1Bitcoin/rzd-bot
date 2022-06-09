package ru.telegram.bot.impl.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchRequestServiceTest {

    private final SearchRequestService searchRequestService = new SearchRequestService();

    @Test
    void checkCorrectDirection() {
    }

    @Test
    void parseDirection() {
        // given
        String direction = "Москва-Саратов";
        List<String> expectedList = Arrays.asList("Москва", "Саратов");

        // when
        List<String> actualList = searchRequestService.parseDirection(direction);

        // then
        assertEquals(expectedList, actualList);
    }
}