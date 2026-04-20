package com.payflow.payment_processor.core.ports.outbound;

import com.payflow.payment_processor.core.domain.Payment;

import java.util.Optional;

public interface PaymentRepositoryPort {

    public Payment save(Payment payment);

    public Optional<Payment> findByIdempotencyKey(String key);
}
