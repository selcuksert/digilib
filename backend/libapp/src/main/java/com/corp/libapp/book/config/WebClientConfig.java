package com.corp.libapp.book.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private final BookConfig bookConfig;

    public WebClientConfig(BookConfig bookConfig) {
        this.bookConfig = bookConfig;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(bookConfig.url().base())
                .build();
    }
}
