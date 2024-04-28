package com.corp.libapp.inventory.event;

import org.springframework.modulith.events.Externalized;

@Externalized("inventory-updated::#{#this.isbn()}")
public record InventoryUpdated(String isbn, Status status, String message) {
}
