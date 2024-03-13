package com.nagarro.ubermodels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Status {

    private String status;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String is_offline_until;

    @JsonIgnoreProperties(ignoreUnknown = true)
    private String reason;
}