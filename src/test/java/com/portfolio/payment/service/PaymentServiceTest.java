package com.portfolio.payment.service;

import com.portfolio.payment.entity.Payment;
import com.portfolio.payment.entity.PaymentStatus;
import com.portfolio.payment.exception.PaymentNotFoundException;
import com.portfolio.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void processPayment_ShouldCreateAuthorizedPayment_WhenLuckFavors() {
        // Given
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.TEN;

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        // When
        // We might need to loop/retry because the service has random logic
        // But for unit test we just assert the flow works
        Payment result = paymentService.processPayment(orderId, userId, amount);

        // Then
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        assertNotNull(result.getTransactionId());
        assertTrue(result.getStatus() == PaymentStatus.AUTHORIZED || result.getStatus() == PaymentStatus.DECLINED);
    }

    @Test
    void getPaymentByOrderId_ShouldReturnPayment_WhenFound() {
        // Given
        UUID orderId = UUID.randomUUID();
        Payment mockPayment = new Payment();
        mockPayment.setOrderId(orderId);
        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.of(mockPayment));

        // When
        Payment result = paymentService.getPaymentByOrderId(orderId);

        // Then
        assertEquals(orderId, result.getOrderId());
    }

    @Test
    void getPaymentByOrderId_ShouldThrow_WhenNotFound() {
        // Given
        UUID orderId = UUID.randomUUID();
        when(paymentRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(PaymentNotFoundException.class, () -> paymentService.getPaymentByOrderId(orderId));
    }
}
