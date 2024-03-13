package com.nagarro.models;

import com.nagarro.enums.Disposition;
import lombok.Data;

import java.util.List;

/*
 *   CREATED BY GARVIT RAJPUT
 *   Date: 16 Jan, 2024
 *
 * */

@Data
public class StoreSpecialHoursPerDisposition {

    private Disposition disposition;

    private List<StoreSpecialHours> specialHours;

}