package com.payflow.payment_processor.infrastructure.adapters.inbound;

import java.math.BigDecimal;

public record PaymentRequest(BigDecimal amount, String currency, String idempotencyKey) {
}
