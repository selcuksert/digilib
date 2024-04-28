package com.corp.libapp.inventory.service;

import com.corp.libapp.inventory.model.Inventory;
import com.corp.libapp.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<Inventory> getAddedBooks() {
        return inventoryRepository.findAllByStatusEqualsIsAdded();
    }

    public void deleteBookByISBN(String isbn) {
        inventoryRepository.deleteById(isbn);
    }

    public boolean existsBookByISBN(String isbn) {
        return inventoryRepository.existsById(isbn);
    }
}
