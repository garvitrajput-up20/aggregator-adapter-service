package com.nagarro.models;

import lombok.Data;

import java.time.LocalDate;

/*
 *
 * CREATED BY GARVIT RAJPUT
 *   Date: 16 Jan, 2024
 *
 * */

@Data
public class StoreSpecialHours {

    private LocalDate day;

    private TimePeriod hours;

}