package com.corp.libapp.search.service;

import com.corp.libapp.search.config.SearchConfig;
import com.corp.libapp.search.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QueryBookService {

    private final RestTemplate restTemplate;
    private final SearchConfig searchConfig;

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

        ResponseEntity<Map<String, Book>> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.GET, null, responseType);
        Map<String, Book> responseBody = response.getBody();

        Map<String, Book> modifiedResponse = new HashMap<>();
        assert responseBody != null;
        // Replace ISBN: prefix in OpenLibrary API response
        responseBody.forEach((isbnToReplace, book) -> modifiedResponse.put(isbnToReplace.replace("ISBN:", ""), book));

        return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders()).body(modifiedResponse);
    }
}
