package shipping.service;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shipping.messaging.ShippingCreatedProducer;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShippingService {
    private final ShippingCreatedProducer shippingCreatedProducer;

    public OrderDTO packedAndShipped(OrderDTO orderDTO) {
        OrderDTO dto =  new OrderDTO(
                orderDTO.getOrderId(),
                orderDTO.getOrderCreatedDate(),
                orderDTO.getOrderPrice(),
                orderDTO.isPaidFor(),
                true,
                true
                );
        log.info("Заказ {} успешно упакован и отправлен покупателю", dto.getOrderId());
        shippingCreatedProducer.sendMessage(dto);
        return dto;
    }
}
