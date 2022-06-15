package ru.telegram.bot.impl.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TrainStation {
    @JsonProperty(value = "n")
    private String name;

    @JsonProperty(value = "c")
    private Integer code;
}
