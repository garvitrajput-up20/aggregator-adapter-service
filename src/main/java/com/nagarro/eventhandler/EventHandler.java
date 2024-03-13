package com.nagarro.eventhandler;

import com.fasterxml.jackson.databind.JsonNode;

public interface EventHandler {
    void handleEvent(JsonNode event);
}
