package com.corp.libapp.search.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.corp.digilib.search")
public record SearchConfig(URL url, CORS cors) {
    public record URL(String base, String query) {
    }
    public record CORS(String allowedOrigins, String allowedMethods, String allowedHeaders, long maxAge) {}
}
