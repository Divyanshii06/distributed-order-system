package com.example.orderprocessing.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private String orderReference;
    private String status;
    private String message;
    private BigDecimal amount;
    private OffsetDateTime processedAt;
}
