package ru.telegram.bot.impl.vo;

import lombok.Data;

/**
 * Направление для поиска
 */
@Data
public class Route {
    private final String from;
    private final String to;
}
