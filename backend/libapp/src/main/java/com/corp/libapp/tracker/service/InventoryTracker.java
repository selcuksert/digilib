package com.corp.libapp.tracker.service;

import com.corp.libapp.inventory.event.InventoryUpdated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryTracker {
    private static final Logger LOG = LoggerFactory.getLogger(InventoryTracker.class);

    @ApplicationModuleListener
    void on(InventoryUpdated event) {
        LOG.info("Inventory updated event: {} {} {}.", event.isbn(), event.status(), event.message());
    }

}
