package com.payflow.payment_processor.infrastructure.config;

import com.payflow.payment_processor.core.ports.inbound.ProcessPaymentUseCase;
import com.payflow.payment_processor.core.ports.outbound.PaymentExternalProviderPort;
import com.payflow.payment_processor.core.ports.outbound.PaymentRepositoryPort;
import com.payflow.payment_processor.core.ProcessPaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Bean
    public ProcessPaymentUseCase processPaymentUseCase(PaymentRepositoryPort repositoryPort, PaymentExternalProviderPort externalProviderPort) {
        return  new ProcessPaymentService(repositoryPort, externalProviderPort);
    }
}
