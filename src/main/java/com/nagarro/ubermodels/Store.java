package com.nagarro.ubermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Store {
    private String id;
    private String name;
    private Contact contact;
    private Location location;
    private String timezone;
    private PrepTimes prep_times;
    private String onboarding_status;
    private Object fulfillment_type_availability;

}