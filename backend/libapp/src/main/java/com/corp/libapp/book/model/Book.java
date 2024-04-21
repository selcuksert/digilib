package com.corp.libapp.book.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record Book(String url, String key, String title,
                   String subtitle, List<UrlName> authors, List<UrlName> subjects,
                   List<UrlName> subjectPlaces, String publishDate, Cover cover,
                   List<Name> publishers, int numberOfPages, Identifier identifiers) {
}
