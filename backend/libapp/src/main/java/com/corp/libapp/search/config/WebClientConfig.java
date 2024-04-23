package com.corp.libapp.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private final SearchConfig searchConfig;

    public WebClientConfig(SearchConfig searchConfig) {
        this.searchConfig = searchConfig;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(searchConfig.url().base())
                .build();
    }
}
