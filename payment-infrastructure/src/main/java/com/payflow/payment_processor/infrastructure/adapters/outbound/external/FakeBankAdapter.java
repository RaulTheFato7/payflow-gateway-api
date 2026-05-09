package com.payflow.payment_processor.infrastructure.adapters.outbound.external;

import com.payflow.payment_processor.core.domain.Payment;
import com.payflow.payment_processor.core.domain.PaymentStatus;
import com.payflow.payment_processor.core.ports.outbound.PaymentExternalProviderPort;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FakeBankAdapter implements PaymentExternalProviderPort {

    @Retryable(
            retryFor = { RuntimeException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000, multiplier = 2))
    @Override
    public PaymentStatus authorize(Payment payment) {
        System.out.println(">>> Tentando autorizar no banco externo...");
        if (Math.random() < 0.5) {
            System.err.println(">>> Falha temporária de rede!");
            throw new RuntimeException("Conexão perdida");
        }
        return PaymentStatus.APPROVED;
    }

    @Recover
    public PaymentStatus recover(RuntimeException e, Payment payment) {
        System.err.println(">>> Retry esgotado após 3 tentativas. Motivo: " + e.getMessage());
        return PaymentStatus.FAILED;
    }
}
