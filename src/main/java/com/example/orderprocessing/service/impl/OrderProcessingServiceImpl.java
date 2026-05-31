package com.example.orderprocessing.service.impl;

import com.example.orderprocessing.dto.OrderRequestDto;
import com.example.orderprocessing.dto.OrderResponseDto;
import com.example.orderprocessing.entity.OrderEntity;
import com.example.orderprocessing.entity.TransactionLog;
import com.example.orderprocessing.exception.OrderProcessingException;
import com.example.orderprocessing.repository.OrderRepository;
import com.example.orderprocessing.repository.TransactionLogRepository;
import com.example.orderprocessing.service.ExternalValidationService;
import com.example.orderprocessing.service.OrderProcessingService;
import com.example.orderprocessing.service.OrderRoutingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class OrderProcessingServiceImpl implements OrderProcessingService {

    private final OrderRepository orderRepository;
    private final TransactionLogRepository transactionLogRepository;
    private final ExternalValidationService externalValidationService;
    private final OrderRoutingService orderRoutingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponseDto processOrder(OrderRequestDto requestDto) {
        if (!externalValidationService.validateCustomer(requestDto.getCustomerId())) {
            throw new OrderProcessingException("Customer validation failed for customerId=" + requestDto.getCustomerId());
        }

        if (!externalValidationService.checkInventory("DEFAULT-PRODUCT", requestDto.getQuantity())) {
            throw new OrderProcessingException("Inventory is insufficient for quantity=" + requestDto.getQuantity());
        }

        OrderResponseDto routingResult = orderRoutingService.route(requestDto);

        OrderEntity orderEntity = OrderEntity.builder()
                .orderReference(requestDto.getOrderReference())
                .orderType(requestDto.getOrderType())
                .customerId(requestDto.getCustomerId())
                .quantity(requestDto.getQuantity())
                .amount(requestDto.getAmount())
                .status(routingResult.getStatus())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        orderRepository.save(orderEntity);
        recordTransaction(orderEntity.getOrderReference(), "ORDER_CREATED", routingResult.getMessage());

        return OrderResponseDto.builder()
                .orderReference(orderEntity.getOrderReference())
                .status(orderEntity.getStatus())
                .message(routingResult.getMessage())
                .amount(orderEntity.getAmount())
                .processedAt(OffsetDateTime.now())
                .build();
    }

    private void recordTransaction(String orderReference, String event, String details) {
        TransactionLog log = TransactionLog.builder()
                .orderReference(orderReference)
                .event(event)
                .details(details)
                .createdAt(OffsetDateTime.now())
                .build();
        transactionLogRepository.save(log);
    }
}
