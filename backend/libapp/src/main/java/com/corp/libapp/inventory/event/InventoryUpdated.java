package com.corp.libapp.inventory.event;

public record InventoryUpdated(String isbn, Status status, String message) {
}
