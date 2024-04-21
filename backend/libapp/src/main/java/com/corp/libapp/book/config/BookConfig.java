package com.corp.libapp.book.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.corp.digilib.book")
public record BookConfig(URL url) {
    public record URL(String base, String query) {
    }
}
