package payment.messaging;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import payment.service.PaymentService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = "${app.kafka.producer-topic}")
    public void listen(OrderDTO dto) {
        log.info("Сервис оплаты получил информацию о заказе {}", dto.getOrderId());
        paymentService.createPayment(dto);
    }
}
