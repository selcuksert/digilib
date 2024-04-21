package com.corp.libapp.book.service;

import com.corp.libapp.book.config.BookConfig;
import com.corp.libapp.book.model.Book;
import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class QueryBookService {

    private final WebClient webClient;
    private final BookConfig bookConfig;

    public QueryBookService(@NonNull WebClient webClient, @NonNull BookConfig bookConfig) {
        this.webClient = webClient;
        this.bookConfig = bookConfig;
    }

    public Mono<Map<String, Book>> queryBookWithISBN(String isbn) {
        return webClient.get().uri(uriBuilder ->
                        uriBuilder.path(bookConfig.url().query())
                                .queryParam("bibkeys", "ISBN:" + isbn)
                                .queryParam("format", "json")
                                .queryParam("jscmd", "data")
                                .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(new ParameterizedTypeReference<>() {
                });
    }
}
