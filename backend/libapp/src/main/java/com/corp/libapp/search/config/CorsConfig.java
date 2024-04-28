package com.corp.libapp.search.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig {

    private final SearchConfig searchConfig;

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
