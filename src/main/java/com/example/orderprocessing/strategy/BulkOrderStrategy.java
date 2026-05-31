package com.example.orderprocessing.strategy;

import com.example.orderprocessing.dto.OrderRequestDto;
import com.example.orderprocessing.dto.OrderResponseDto;
import com.example.orderprocessing.model.OrderType;
import org.springframework.stereotype.Component;

@Component
public class BulkOrderStrategy implements OrderProcessingStrategy {

    @Override
    public OrderType getSupportedOrderType() {
        return OrderType.BULK;
    }

    @Override
    public OrderResponseDto process(OrderRequestDto requestDto) {
        String message = "Bulk order requires staged fulfillment and manual review for large quantities.";
        return OrderResponseDto.builder()
                .orderReference(requestDto.getOrderReference())
                .status("REVIEW_PENDING")
                .message(message)
                .amount(requestDto.getAmount())
                .build();
    }
}
