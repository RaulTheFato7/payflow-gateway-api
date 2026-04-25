package com.payflow.payment_processor.core.ports.inbound;

import com.payflow.payment_processor.core.domain.Payment;

public interface ProcessPaymentUseCase {

    public Payment execute(Payment payment);
}
