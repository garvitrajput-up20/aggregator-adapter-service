package com.nagarro.eventhandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.nagarro.service.UberStoreStatusService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
public class StatusChangeEventHandler implements EventHandler {

    private final UberStoreStatusService uberStoreStatusService;

    @Inject
    public StatusChangeEventHandler(UberStoreStatusService uberStoreStatusService) {
        this.uberStoreStatusService = uberStoreStatusService;
    }
    @Override
    public void handleEvent(JsonNode event) {
        log.info("Handling Status Change Event: {}", event);
        String status = String.valueOf(event.path("detail").path("payload"));
        String storeId = event.path("detail").path("context").path("aggregatorStoreId").asText();
        if (uberStoreStatusService == null) {
            log.error("UberStoreStatusService is not injected properly");
            return;
        }
        log.info("Status: {}", status);
        log.info("StoreID: {}", storeId);
        uberStoreStatusService.setStoreStatus(storeId, status);
    }
}