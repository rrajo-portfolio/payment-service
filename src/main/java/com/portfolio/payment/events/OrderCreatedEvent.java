package com.portfolio.payment.events;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
    UUID orderId,
    UUID userId,
    String userName,
    String userEmail,
    String status,
    BigDecimal totalAmount,
    String currency,
    OffsetDateTime createdAt,
    List<OrderItem> items
) {
    public record OrderItem(
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal price
    ) {}
}
