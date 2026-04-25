package com.payflow.payment_processor.core.usecase;

import com.payflow.payment_processor.core.ProcessPaymentService;
import com.payflow.payment_processor.core.domain.Payment;
import com.payflow.payment_processor.core.domain.PaymentStatus;
import com.payflow.payment_processor.core.ports.outbound.PaymentExternalProviderPort;
import com.payflow.payment_processor.core.ports.outbound.PaymentRepositoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessPaymentServiceTest {

    @Mock
    private PaymentRepositoryPort repositoryPort;

    @Mock
    private PaymentExternalProviderPort externalProviderPort;

    @InjectMocks
    private ProcessPaymentService processPaymentService;

    @Test
    @DisplayName("Deve retornar pagamento existente quando a chave de idempotencia ja existir")
    void deveRetornarPagamentoExistente() {
        String originalKey = "key-221";
        Payment existentPayment = Payment.builder().idempotencyKey(originalKey).status(PaymentStatus.APPROVED).build();

        when(repositoryPort.findByIdempotencyKey(originalKey)).thenReturn(Optional.of(existentPayment));

        Payment request = Payment.builder().idempotencyKey(originalKey).build();
        Payment result = processPaymentService.execute(request);

        assertEquals(existentPayment, result);

        verify(externalProviderPort, never()).authorize(any());
        verify(repositoryPort, times(1)).findByIdempotencyKey(originalKey);
    }

    @Test
    @DisplayName("Deve processar um novo pagamento com sucesso quando a chave for inedita")
    void deveProcessarNovoPagamentoComSucesso() {
        Payment request = Payment.builder().idempotencyKey("new-key-221").amount(new BigDecimal("100.00")).currency("BRL").build();

        when(repositoryPort.findByIdempotencyKey(anyString())).thenReturn(Optional.empty());
        when((externalProviderPort.authorize(any(Payment.class)))).thenReturn(PaymentStatus.APPROVED);
        when(repositoryPort.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment result = processPaymentService.execute(request);

        assertNotNull(result);
        assertEquals(PaymentStatus.APPROVED, result.getStatus());

        verify(repositoryPort, times(1)).findByIdempotencyKey("new-key-221");
        verify(externalProviderPort, times(1)).authorize(any(Payment.class));
        verify(repositoryPort, times(2)).save(any(Payment.class));
    }
}
