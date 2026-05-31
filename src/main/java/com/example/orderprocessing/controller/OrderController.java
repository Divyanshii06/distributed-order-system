package com.example.orderprocessing.controller;

import com.example.orderprocessing.dto.OrderRequestDto;
import com.example.orderprocessing.dto.OrderResponseDto;
import com.example.orderprocessing.service.OrderProcessingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProcessingService orderProcessingService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto requestDto) {
        OrderResponseDto responseDto = orderProcessingService.processOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
