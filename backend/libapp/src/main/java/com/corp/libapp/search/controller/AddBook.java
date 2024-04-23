package com.corp.libapp.search.controller;

import com.corp.libapp.search.service.AddBookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class AddBook {
    private final AddBookService addBookService;

    public AddBook(AddBookService addBookService) {
        this.addBookService = addBookService;
    }

    @PostMapping(path = "/{isbn}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addBook(@PathVariable String isbn) {
        addBookService.addBook(isbn);
        return ResponseEntity.ok().body(isbn);
    }
}
