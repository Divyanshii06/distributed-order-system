package com.example.orderprocessing.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "transaction_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String orderReference;

    @Column(nullable = false)
    private String event;

    @Column(nullable = false, length = 1024)
    private String details;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
}
