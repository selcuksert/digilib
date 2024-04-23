package com.corp.libapp.search.controller;

import com.corp.libapp.search.model.Book;
import com.corp.libapp.search.service.QueryBookService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/book")
public class QueryBook {
    private final QueryBookService queryBookService;

    public QueryBook(QueryBookService queryBookService) {
        this.queryBookService = queryBookService;
    }

    @GetMapping(value = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Book>> queryBook(@PathVariable String isbn) {
        return ResponseEntity.ok(queryBookService.queryBookWithISBN(isbn).getBody());
    }
}
