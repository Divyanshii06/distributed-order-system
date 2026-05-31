package com.example.orderprocessing.service;

public interface ExternalValidationService {
    boolean validateCustomer(String customerId);
    boolean checkInventory(String productCode, int quantity);
}
