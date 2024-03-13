package com.nagarro;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.eventhandler.EventHandler;
import com.nagarro.eventhandler.EventHandlerFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@ApplicationScoped
public class Handler implements RequestStreamHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    EventHandlerFactory eventHandlerFactory;

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        log.info("Event Received");

        try {
            JsonNode inputEvent = objectMapper.readTree(inputStream);
            String eventType = inputEvent.path("detail").path("context").path("eventType").asText();
            String storeId = inputEvent.path("detail").path("context").path("aggregatorStoreId").asText();
            EventHandler eventHandler = eventHandlerFactory.createEventHandler(eventType);

            if (eventHandler != null) {
                log.info("Event Type: {}", eventType);
                eventHandler.handleEvent(inputEvent);
            } else {
                log.warn("Unknown event type: {}", eventType);
            }
        } catch (Exception e) {
            log.error("Error Processing Payload", e);
        }

        log.info("Lambda Executed");
    }
}
