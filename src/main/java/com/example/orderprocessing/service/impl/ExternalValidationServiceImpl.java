package com.example.orderprocessing.service.impl;

import com.example.orderprocessing.exception.OrderProcessingException;
import com.example.orderprocessing.service.ExternalValidationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ExternalValidationServiceImpl implements ExternalValidationService {

    private final WebClient externalApiWebClient;

    @PostConstruct
    public void init() {
        // Initialize or validate connectivity for the mock external API.
    }

    @Override
    public boolean validateCustomer(String customerId) {
        try {
            return externalApiWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/customers/{customerId}/validate").build(customerId))
                    .retrieve()
                    .bodyToMono(CustomerValidationResponse.class)
                    .map(CustomerValidationResponse::isValid)
                    .block();
        } catch (Exception ex) {
            throw new OrderProcessingException("Failed to validate customer against external service", ex);
        }
    }

    @Override
    public boolean checkInventory(String productCode, int quantity) {
        try {
            return externalApiWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/inventory/{productCode}/availability").build(productCode))
                    .retrieve()
                    .bodyToMono(InventoryAvailabilityResponse.class)
                    .map(response -> response.getAvailableQuantity() >= quantity)
                    .block();
        } catch (Exception ex) {
            throw new OrderProcessingException("Failed to check inventory availability", ex);
        }
    }

    private record CustomerValidationResponse(boolean valid) {
    }

    private record InventoryAvailabilityResponse(int availableQuantity) {
    }
}
