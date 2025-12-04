package com.portfolio.payment.controller;

import com.portfolio.payment.entity.Payment;
import com.portfolio.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestParam UUID orderId, 
                                                 @RequestParam UUID userId, 
                                                 @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(paymentService.processPayment(orderId, userId, amount));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> getPayment(@PathVariable UUID orderId) {
        return ResponseEntity.ok(paymentService.getPaymentByOrderId(orderId));
    }
}
