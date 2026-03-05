package shipping.messaging;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShippingCreatedProducer {

    @Value("${app.kafka.producer-topic}")
    private String topicName;

    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;
    public void sendMessage(OrderDTO dto) {
        kafkaTemplate.send(topicName, dto)
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        log.info("Сообщение об успешной отправке заказа {} пользователю отправлено в сервис уведомлений", dto.getOrderId());
                    } else {
                        log.error("Ошибка отправки сообщения об отправке товара\n Ошибка: {}",throwable.getMessage());
                    }
                });
    }
}
