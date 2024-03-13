package com.nagarro.eventhandler;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@Slf4j
@ApplicationScoped
public class EventRouterResource {

    @Inject
    EventHandlerFactory eventHandlerFactory;
    @Incoming("event-channel")
    public void processEvent(String eventType, JsonNode eventJson) {
        EventHandler eventHandler = eventHandlerFactory.createEventHandler(eventType);
        if (eventHandler != null) {
            eventHandler.handleEvent(eventJson);
        } else {
            log.info("Unknown event type: {}", eventType);
        }
    }
}