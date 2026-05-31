package com.example.orderprocessing.service;

import com.example.orderprocessing.dto.OrderRequestDto;
import com.example.orderprocessing.dto.OrderResponseDto;

public interface OrderRoutingService {
    OrderResponseDto route(OrderRequestDto requestDto);
}
