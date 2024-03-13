package com.nagarro.convertor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.ubermodels.Store;

import java.io.IOException;

public class StoreConverter {
    private final ObjectMapper objectMapper;

    public StoreConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public Store convertJsonToStore(String json) throws IOException {
        return objectMapper.readValue(json, Store.class);
    }
}