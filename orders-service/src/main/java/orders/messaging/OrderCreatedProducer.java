package orders.messaging;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedProducer {

    @Value("${app.kafka.producer-topic}")
    private String topicName;

    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    public void sendMessage(OrderDTO dto) {

        kafkaTemplate.send(topicName, dto)
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        log.info("Сообщение об успешном формировании заказа {} отправлено в сервис оплаты",dto.getOrderId());
                    } else {
                        log.error("Ошибка отправки сообщения о формировании заказа\n Ошибка: {}",throwable.getMessage());
                    }
                });
    }
}
