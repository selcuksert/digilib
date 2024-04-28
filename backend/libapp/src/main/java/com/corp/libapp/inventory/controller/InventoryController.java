package com.corp.libapp.inventory.controller;

import com.corp.libapp.inventory.model.Inventory;
import com.corp.libapp.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping(path = "/added", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Inventory>> getAddedBooks() {
        return ResponseEntity.ok().body(inventoryService.getAddedBooks());
    }

    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable("isbn") String isbn) {
        inventoryService.deleteBookByISBN(isbn);
        return ResponseEntity.ok().build();
    }
}
