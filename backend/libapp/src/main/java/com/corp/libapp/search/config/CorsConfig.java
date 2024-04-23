package com.corp.libapp.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    private final SearchConfig searchConfig;

    public CorsConfig(SearchConfig searchConfig) {
        this.searchConfig = searchConfig;
    }

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(searchConfig.cors().allowedOrigins())
                        .allowedMethods(searchConfig.cors().allowedMethods())
                        .allowedHeaders(searchConfig.cors().allowedHeaders())
                        .maxAge(searchConfig.cors().maxAge());
            }
        };
    }
}
