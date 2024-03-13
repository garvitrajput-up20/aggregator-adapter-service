package com.nagarro.eventhandler;

import com.nagarro.service.UberStoreStatusService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventHandlerFactory {
    @Inject
    UberStoreStatusService uberStoreStatusService;

    public EventHandler createEventHandler(String eventType) {
        if ("AGGR_UBEREATS_STORE_STATUS_CHANGED".equals(eventType)) {
            return new StatusChangeEventHandler(uberStoreStatusService);
        }
        return null;
    }
}