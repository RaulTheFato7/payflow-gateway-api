package com.payflow.payment_processor.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private UUID id;
    private BigDecimal amount;
    private String currency;
    private String idempotencyKey;
    private PaymentStatus status;
    private LocalDateTime createdAt;
}
