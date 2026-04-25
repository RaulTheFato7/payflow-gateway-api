package com.payflow.payment_processor.core.ports.outbound;

import com.payflow.payment_processor.core.domain.Payment;
import com.payflow.payment_processor.core.domain.PaymentStatus;

public interface PaymentExternalProviderPort {

    PaymentStatus authorize(Payment payment);
}
