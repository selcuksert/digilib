package com.corp.libapp.search.service;

import com.corp.libapp.search.config.SearchConfig;
import com.corp.libapp.search.model.Book;
import lombok.NonNull;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class QueryBookService {

    private final RestTemplate restTemplate;
    private final SearchConfig searchConfig;

    public QueryBookService(@NonNull RestTemplate restTemplate, @NonNull SearchConfig searchConfig) {
        this.restTemplate = restTemplate;
        this.searchConfig = searchConfig;
    }

    public ResponseEntity<Map<String, Book>> queryBookWithISBN(String isbn) {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme(searchConfig.url().scheme())
                .host(searchConfig.url().host())
                .path(searchConfig.url().queryPath())
                .queryParam("bibkeys", "ISBN:" + isbn)
                .queryParam("format", "json")
                .queryParam("jscmd", "data")
                .build().encode();

        ParameterizedTypeReference<Map<String, Book>> responseType = new ParameterizedTypeReference<>() {
        };

        return restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, responseType);
    }
}
