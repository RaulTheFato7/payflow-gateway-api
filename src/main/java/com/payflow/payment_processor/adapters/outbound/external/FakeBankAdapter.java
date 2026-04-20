package com.payflow.payment_processor.adapters.outbound.external;

import com.payflow.payment_processor.core.domain.Payment;
import com.payflow.payment_processor.core.domain.PaymentStatus;
import com.payflow.payment_processor.core.ports.outbound.PaymentExternalProviderPort;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FakeBankAdapter implements PaymentExternalProviderPort {
    @Override
    public PaymentStatus authorize(Payment payment) {
        return new Random().nextBoolean() ? PaymentStatus.APPROVED : PaymentStatus.FAILED;
    }
}
