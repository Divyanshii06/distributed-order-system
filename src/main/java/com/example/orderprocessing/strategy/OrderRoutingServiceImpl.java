package com.example.orderprocessing.strategy;

import com.example.orderprocessing.dto.OrderRequestDto;
import com.example.orderprocessing.dto.OrderResponseDto;
import com.example.orderprocessing.exception.OrderProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderRoutingServiceImpl implements com.example.orderprocessing.service.OrderRoutingService {

    private final List<OrderProcessingStrategy> strategies;

    @Override
    public OrderResponseDto route(OrderRequestDto requestDto) {
        return strategies.stream()
                .filter(strategy -> strategy.getSupportedOrderType().equals(requestDto.getOrderType()))
                .findFirst()
                .orElseThrow(() -> new OrderProcessingException("Unsupported order type: " + requestDto.getOrderType()))
                .process(requestDto);
    }
}
