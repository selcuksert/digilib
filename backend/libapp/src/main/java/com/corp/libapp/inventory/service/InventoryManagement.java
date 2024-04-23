package com.corp.libapp.inventory.service;

import com.corp.libapp.search.event.BookAdded;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryManagement {
    private static final Logger LOG = LoggerFactory.getLogger(InventoryManagement.class);

    @ApplicationModuleListener
    void on(BookAdded event) {
        var isbn = event.isbn();
        LOG.info("Added book to inventory with ISBN: {}.", isbn);
    }

}
