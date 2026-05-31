package com.example.orderprocessing.dto;

import com.example.orderprocessing.model.OrderType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    @NotBlank
    private String orderReference;

    @NotNull
    private OrderType orderType;

    @NotBlank
    private String customerId;

    @NotNull
    @Positive
    private Integer quantity;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;
}
