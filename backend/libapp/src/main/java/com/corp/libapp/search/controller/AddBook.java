package com.corp.libapp.search.controller;

import com.corp.libapp.search.service.AddBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class AddBook {
    private final AddBookService addBookService;

    @PostMapping(path = "/{isbn}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addBook(@PathVariable String isbn) {
        addBookService.addBook(isbn);
        return ResponseEntity.ok().body(isbn);
    }
}
