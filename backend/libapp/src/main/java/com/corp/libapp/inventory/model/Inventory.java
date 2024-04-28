package com.corp.libapp.inventory.model;

import com.corp.libapp.inventory.event.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Data
public class Inventory {
    @Id
    @Column
    private String isbn;
    @Column
    private String title;
    @Column
    private String url;
    @Column
    @ElementCollection
    private List<String> authors;
    @Column
    private LocalDateTime updatedAt;
    @Column
    private Status status;
}
