package com.corp.libapp.inventory.service;

import com.corp.libapp.inventory.event.InventoryUpdated;
import com.corp.libapp.inventory.event.Status;
import com.corp.libapp.inventory.model.Inventory;
import com.corp.libapp.inventory.repository.InventoryRepository;
import com.corp.libapp.search.event.BookAdded;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryManagement {
    private static final Logger LOG = LoggerFactory.getLogger(InventoryManagement.class);

    private final ApplicationEventPublisher events;
    private final InventoryRepository inventoryRepository;
    private final ObjectMapper mapper;

    @ApplicationModuleListener
    void on(BookAdded event) {
        String isbn = "";

        try {
            isbn = event.isbn();
            LOG.info("Added book to inventory with ISBN: {}.", isbn);
            var inventory = new Inventory();
            inventory.setIsbn(isbn);
            inventory.setAuthors(event.authors());
            inventory.setTitle(event.title());
            inventory.setUrl(event.url());
            inventory.setUpdatedAt(LocalDateTime.now());
            inventory.setStatus(Status.ADDED);
            inventoryRepository.save(inventory);
            events.publishEvent(new InventoryUpdated(isbn, Status.ADDED, "Success"));

            if (LOG.isDebugEnabled()) {
                Optional<Inventory> result = inventoryRepository.findById(isbn);
                if (result.isPresent()) {
                    LOG.debug("Added book to inventory: {}.", mapper.writeValueAsString(result.get()));
                }
            }
        } catch (Exception e) {
            events.publishEvent(new InventoryUpdated(isbn, Status.ERROR, e.getMessage()));
        }
    }
}
