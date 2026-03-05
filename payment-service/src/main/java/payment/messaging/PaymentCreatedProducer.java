package payment.messaging;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCreatedProducer {

    @Value("${app.kafka.producer-topic}")
    private String topicName;

    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;
    public void sendMessage(OrderDTO orderDTO) {
        kafkaTemplate.send(topicName, orderDTO)
                .whenComplete((result, throwable) -> {
                    if (throwable == null) {
                        log.info("Сообщение об успешной оплате по заказу {} отправлено на сервис уведомлений", orderDTO.getOrderId());
                    } else {
                        log.error("Ошибка отправки сообщения о проведении оплаты\n Ошибка: {}",throwable.getMessage());
                    }
                });
    }
}
