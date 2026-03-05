package payment.service;

import com.fasterxml.jackson.databind.JsonDeserializer;
import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.exception.PaymentProcessingException;
import payment.messaging.OrderCreatedConsumer;
import payment.messaging.PaymentCreatedProducer;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentCreatedProducer paymentCreatedProducer;

    @Transactional
    public void createPayment(OrderDTO dto) {
        try {
            OrderDTO paymentOrderDTO = new OrderDTO(
                    dto.getOrderId(),
                    dto.getOrderCreatedDate(),
                    dto.getOrderPrice(),
                    true,
                    dto.isPacked(),
                    dto.isShipped()
            );
            Thread.sleep(2000);
            log.info("Проведена оплата заказа {}",paymentOrderDTO.getOrderId());
            paymentCreatedProducer.sendMessage(paymentOrderDTO);
        } catch (Exception e) {
            log.error("Ошибка оплаты заказа {}, с ошибкой {}",dto.getOrderId(),e.getMessage());
            throw  new PaymentProcessingException("Проблема с проведением оплаты");
        }

    }
}
