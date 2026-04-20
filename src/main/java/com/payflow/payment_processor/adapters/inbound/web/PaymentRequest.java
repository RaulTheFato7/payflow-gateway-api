package com.payflow.payment_processor.adapters.inbound.web;

import java.math.BigDecimal;

public record PaymentRequest(BigDecimal amount, String currency, String idempotencyKey) {
}
