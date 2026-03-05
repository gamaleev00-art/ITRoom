package orders.service;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orders.messaging.OrderCreatedProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderCreatedProducer orderCreatedProducer;

    @Transactional
    public OrderDTO createOrder() {
        BigDecimal orderPrice = BigDecimal.valueOf(Math.random()*1000);
        String createDate = LocalDateTime.now().toString();
        OrderDTO dto = new OrderDTO(UUID.randomUUID(),
                createDate,
                orderPrice,
                false,
                false,
                false
        );
        log.info("Заказ {} успешно сформирован", dto.getOrderId());
        orderCreatedProducer.sendMessage(dto);
        return dto;
    }
}
