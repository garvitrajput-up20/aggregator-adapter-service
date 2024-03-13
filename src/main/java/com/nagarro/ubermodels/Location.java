package com.nagarro.ubermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String latitude;
    private String longitude;
    private String street_address_line_one;
    private String city;
    private String country;
    private String postal_code;

}