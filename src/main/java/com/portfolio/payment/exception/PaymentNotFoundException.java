package com.portfolio.payment.exception;

import java.util.UUID;

/**
 * Custom exception to expose a clear semantic error when the payment
 * associated to an order cannot be found.
 */
public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException(UUID orderId) {
        super("Payment was not found for order: " + orderId);
    }
}
