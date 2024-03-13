package com.nagarro.models;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.Data;

import java.time.LocalTime;

/*
*   CREATED BY GARVIT RAJPUT
*   Date: 16 Jan, 2024
*
* */

@Data
public class TimePeriod {

    @JsonbDateFormat("HH:mm:ss")
    private LocalTime startTime;

    @JsonbDateFormat("HH:mm:ss")
    private LocalTime endTime;
}