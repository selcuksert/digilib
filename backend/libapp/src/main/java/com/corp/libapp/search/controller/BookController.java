package com.corp.libapp.search.controller;

import com.corp.libapp.search.model.Book;
import com.corp.libapp.search.service.BookService;
import com.corp.libapp.search.service.QueryBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService addBookService;
    private final QueryBookService queryBookService;

    @GetMapping(value = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Book>> queryBook(@PathVariable String isbn) {
        return ResponseEntity.ok(queryBookService.queryBookWithISBN(isbn).getBody());
    }

    @PostMapping(path = "/{isbn}")
    public ResponseEntity<String> addBook(@PathVariable String isbn) {
        addBookService.addBook(isbn);
        return ResponseEntity.ok().body(isbn);
    }

    @DeleteMapping(path = "/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        addBookService.deleteBook(isbn);
        return ResponseEntity.ok().body(isbn);
    }
}
