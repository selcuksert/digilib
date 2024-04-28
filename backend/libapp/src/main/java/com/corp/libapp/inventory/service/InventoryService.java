package com.corp.libapp.inventory.service;

import com.corp.libapp.inventory.model.Inventory;
import com.corp.libapp.inventory.repository.InventoryRepository;
import com.corp.libapp.search.event.BookDeleted;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ApplicationEventPublisher events;

    public List<Inventory> getAddedBooks() {
        return inventoryRepository.findAllByStatusEqualsIsAdded();
    }

    @Transactional
    public void deleteBookByISBN(String isbn) {
        inventoryRepository.deleteById(isbn);
        events.publishEvent(new BookDeleted(isbn));
    }

    public boolean existsBookByISBN(String isbn) {
        return inventoryRepository.existsById(isbn);
    }
}
