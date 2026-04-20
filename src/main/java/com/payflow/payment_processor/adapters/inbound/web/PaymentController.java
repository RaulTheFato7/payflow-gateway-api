package com.payflow.payment_processor.adapters.inbound.web;

import com.payflow.payment_processor.core.domain.Payment;
import com.payflow.payment_processor.core.ports.inbound.ProcessPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final ProcessPaymentUseCase processPaymentUseCase;

    @PostMapping
    public Payment create(@RequestBody PaymentRequest request) {

        Payment payment = Payment.builder().amount(request.amount()).currency(request.currency()).idempotencyKey(request.idempotencyKey()).build();
        return processPaymentUseCase.execute(payment);
    }
}
