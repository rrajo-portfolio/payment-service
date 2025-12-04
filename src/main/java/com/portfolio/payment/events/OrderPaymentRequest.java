package com.portfolio.payment.events;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderPaymentRequest(
    UUID orderId,
    UUID userId,
    BigDecimal amount
) {}
