package com.corp.libapp.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Identifier(@JsonProperty("isbn_10") List<String> isbn10, @JsonProperty("isbn_13") List<String> isbn13,
                         List<String> oclc) {
}
