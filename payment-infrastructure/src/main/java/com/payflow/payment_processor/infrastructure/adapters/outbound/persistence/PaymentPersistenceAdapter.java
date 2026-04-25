package com.payflow.payment_processor.infrastructure.adapters.outbound.persistence;

import com.payflow.payment_processor.core.domain.Payment;
import com.payflow.payment_processor.core.ports.outbound.PaymentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentRepositoryPort {

    private final JpaPaymentRepository jpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.setId(payment.getId());
        entity.setAmount(payment.getAmount());
        entity.setCurrency(payment.getCurrency());
        entity.setIdempotencyKey(payment.getIdempotencyKey());
        entity.setStatus(payment.getStatus());
        entity.setCreatedAt(payment.getCreatedAt());

        PaymentEntity saved = jpaRepository.save(entity);

        // atualiza o id do dominio com o id gerado pelo banco
        payment.setId(saved.getId());
        return payment;
    }

    @Override
    public Optional<Payment> findByIdempotencyKey(String key) {
        return jpaRepository.findByIdempotencyKey(key)
                .map(entity -> Payment.builder()
                        .id(entity.getId())
                        .amount(entity.getAmount())
                        .currency(entity.getCurrency())
                        .idempotencyKey(entity.getIdempotencyKey())
                        .status(entity.getStatus())
                        .build());
    }
}
