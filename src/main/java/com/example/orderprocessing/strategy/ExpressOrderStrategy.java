package com.example.orderprocessing.strategy;

import com.example.orderprocessing.dto.OrderRequestDto;
import com.example.orderprocessing.dto.OrderResponseDto;
import com.example.orderprocessing.model.OrderType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExpressOrderStrategy implements OrderProcessingStrategy {

    @Override
    public OrderType getSupportedOrderType() {
        return OrderType.EXPRESS;
    }

    @Override
    public OrderResponseDto process(OrderRequestDto requestDto) {
        BigDecimal expressFee = requestDto.getAmount().multiply(BigDecimal.valueOf(0.08));
        String message = "Express order prioritized. Express fee applied: " + expressFee;
        return OrderResponseDto.builder()
                .orderReference(requestDto.getOrderReference())
                .status("PRIORITY_PROCESSING")
                .message(message)
                .amount(requestDto.getAmount().add(expressFee))
                .build();
    }
}
