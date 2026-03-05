package notifications.messaging;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notifications.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingOrderConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "${app.kafka.consumer-topic}")
    public void listen(OrderDTO dto) {
        log.info("Сообщение о заказе {} получено сервисом уведомлений", dto.getOrderId());
        notificationService.sendNotification(dto);
    }
}
