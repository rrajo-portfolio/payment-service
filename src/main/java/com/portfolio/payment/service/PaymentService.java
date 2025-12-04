package com.portfolio.payment.service;

import com.portfolio.payment.entity.Payment;
import com.portfolio.payment.entity.PaymentStatus;
import com.portfolio.payment.exception.PaymentNotFoundException;
import com.portfolio.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public Payment processPayment(UUID orderId, UUID userId, BigDecimal amount) {
        log.info("Processing payment for Order ID: {}", orderId);

        // Simulate complex logic or external gateway call (Stripe/PayPal)
        boolean success = secureRandom.nextDouble() > 0.1; // 90% success rate

        Payment payment = Payment.builder()
                .orderId(orderId)
                .userId(userId)
                .amount(amount)
                .currency("USD")
                .transactionId(UUID.randomUUID().toString())
                .status(success ? PaymentStatus.AUTHORIZED : PaymentStatus.DECLINED)
                .build();

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(UUID orderId) {
        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(orderId));
    }
}
