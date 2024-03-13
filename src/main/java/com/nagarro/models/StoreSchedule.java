package com.nagarro.models;

import lombok.Data;

import java.util.TimeZone;

/*
 *   CREATED BY GARVIT RAJPUT
 *   Date: 16 Jan, 2024
 *
 * */
@Data
public class StoreSchedule {

    private TimeZone timeZone;

    private StoreHoursPerDisposition storeHours;

}
