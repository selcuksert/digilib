package com.corp.libapp.search.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsConfig implements WebFluxConfigurer {

    private final SearchConfig searchConfig;

    public CorsConfig(SearchConfig searchConfig) {
        this.searchConfig = searchConfig;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(searchConfig.cors().allowedOrigins())
                .allowedHeaders(searchConfig.cors().allowedHeaders())
                .allowedMethods(searchConfig.cors().allowedMethods())
                .maxAge(searchConfig.cors().maxAge());
    }
}
