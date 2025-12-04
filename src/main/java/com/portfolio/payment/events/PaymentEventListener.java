package com.portfolio.payment.events;

import com.portfolio.payment.entity.Payment;
import com.portfolio.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventListener {

    private final PaymentService paymentService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String PAYMENT_RESULT_TOPIC = "payment-results";

    @KafkaListener(topics = "${spring.kafka.consumer.topic:orders-checkout-events}", groupId = "payment-group")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent for order: {}", event.orderId());
        try {
            // Only process if status is PENDING (Assuming Orders Service creates as PENDING)
            Payment payment = paymentService.processPayment(event.orderId(), event.userId(), event.totalAmount());
            
            PaymentResultEvent result = new PaymentResultEvent(
                payment.getOrderId(),
                payment.getTransactionId(),
                payment.getStatus().name()
            );
            
            kafkaTemplate.send(PAYMENT_RESULT_TOPIC, result.orderId().toString(), result);
            log.info("Published PaymentResultEvent for order: {}. Status: {}", payment.getOrderId(), payment.getStatus());
            
        } catch (Exception e) {
            log.error("Error processing payment for order: {}", event.orderId(), e);
            // Ideally publish a failure event here too
        }
    }
}
