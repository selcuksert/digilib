package com.corp.libapp.inventory.repository;


import com.corp.libapp.inventory.event.Status;
import com.corp.libapp.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
    default List<Inventory> findAllByStatusEqualsIsAdded() {
        return findAllByStatusEquals(Status.ADDED);
    }

    List<Inventory> findAllByStatusEquals(Status status);
}
