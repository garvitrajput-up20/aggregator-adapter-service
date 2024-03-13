package com.nagarro.models;

import lombok.Data;

import java.time.DayOfWeek;

/*
 *   CREATED BY GARVIT RAJPUT
 *   Date: 16 Jan, 2024
 *
 * */

@Data
public class StoreHours {

    private DayOfWeek dayOfWeek;

    private TimePeriod timePeriod;

}