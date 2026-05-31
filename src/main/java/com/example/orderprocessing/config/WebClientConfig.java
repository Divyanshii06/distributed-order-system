package com.example.orderprocessing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient externalApiWebClient() {
        return WebClient.builder()
                .baseUrl("https://mock-api.example.com")
                .build();
    }
}
