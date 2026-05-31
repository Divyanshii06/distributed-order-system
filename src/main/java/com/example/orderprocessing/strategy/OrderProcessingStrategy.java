package com.example.orderprocessing.strategy;

import com.example.orderprocessing.dto.OrderRequestDto;
import com.example.orderprocessing.dto.OrderResponseDto;
import com.example.orderprocessing.model.OrderType;

public interface OrderProcessingStrategy {
    OrderType getSupportedOrderType();
    OrderResponseDto process(OrderRequestDto requestDto);
}
