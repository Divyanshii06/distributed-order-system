package com.example.orderprocessing.strategy;

import com.example.orderprocessing.dto.OrderRequestDto;
import com.example.orderprocessing.dto.OrderResponseDto;
import com.example.orderprocessing.model.OrderType;
import org.springframework.stereotype.Component;

@Component
public class StandardOrderStrategy implements OrderProcessingStrategy {

    @Override
    public OrderType getSupportedOrderType() {
        return OrderType.STANDARD;
    }

    @Override
    public OrderResponseDto process(OrderRequestDto requestDto) {
        String message = "Standard order is queued for regular fulfillment.";
        return OrderResponseDto.builder()
                .orderReference(requestDto.getOrderReference())
                .status("PROCESSING")
                .message(message)
                .amount(requestDto.getAmount())
                .build();
    }
}
