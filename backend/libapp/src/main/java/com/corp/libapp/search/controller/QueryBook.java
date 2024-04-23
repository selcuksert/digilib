package com.corp.libapp.search.controller;

import com.corp.libapp.search.model.Book;
import com.corp.libapp.search.service.QueryBookService;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/book")
public class QueryBook {
    private final QueryBookService queryBookService;

    public QueryBook(@NonNull QueryBookService queryBookService) {
        this.queryBookService = queryBookService;
    }

    @GetMapping(value = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, Book>> queryBook(@PathVariable String isbn) {
        return queryBookService.queryBookWithISBN(isbn);
    }
}
