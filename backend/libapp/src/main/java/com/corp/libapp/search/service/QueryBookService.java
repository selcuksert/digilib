package com.corp.libapp.search.service;

import com.corp.libapp.search.config.SearchConfig;
import com.corp.libapp.search.model.Book;
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
    private final SearchConfig searchConfig;

    public QueryBookService(@NonNull WebClient webClient, @NonNull SearchConfig searchConfig) {
        this.webClient = webClient;
        this.searchConfig = searchConfig;
    }

    public Mono<Map<String, Book>> queryBookWithISBN(String isbn) {
        return webClient.get().uri(uriBuilder ->
                        uriBuilder.path(searchConfig.url().query())
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
