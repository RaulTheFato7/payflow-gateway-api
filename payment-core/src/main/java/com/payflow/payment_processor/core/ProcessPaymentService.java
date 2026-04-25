package com.payflow.payment_processor.core;

import com.payflow.payment_processor.core.domain.Payment;
import com.payflow.payment_processor.core.domain.PaymentStatus;
import com.payflow.payment_processor.core.ports.inbound.ProcessPaymentUseCase;
import com.payflow.payment_processor.core.ports.outbound.PaymentExternalProviderPort;
import com.payflow.payment_processor.core.ports.outbound.PaymentRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ProcessPaymentService implements ProcessPaymentUseCase {

    private final PaymentRepositoryPort repositoryPort;
    private final PaymentExternalProviderPort externalProviderPort;

    @Override
    public Payment execute(Payment payment) {
        Optional<Payment> existingPayment = repositoryPort.findByIdempotencyKey(payment.getIdempotencyKey());
        if (existingPayment.isPresent()) {
            return existingPayment.get();
        }
        payment.setStatus(PaymentStatus.PENDING);
        Payment savedPayment = repositoryPort.save(payment);

        PaymentStatus resultStatus = externalProviderPort.authorize(savedPayment);

        savedPayment.setStatus(resultStatus);
        return repositoryPort.save(savedPayment);
    }
}
