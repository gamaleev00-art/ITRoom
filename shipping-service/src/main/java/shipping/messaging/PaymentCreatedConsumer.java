package shipping.messaging;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import shipping.service.ShippingService;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentCreatedConsumer {

    private final ShippingService shippingService;
    @KafkaListener(topics = "${app.kafka.consumer-topic}")
    public void listen(OrderDTO orderDTO) {
        log.info("Информация об успешно проведенной оплате заказа {} поступила на сервис упаковки", orderDTO.getOrderId());
            shippingService.packedAndShipped(orderDTO);
    }
}
