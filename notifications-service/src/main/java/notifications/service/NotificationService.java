package notifications.service;

import dto_order.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public void sendNotification(OrderDTO dto) {
        log.info("Уведомление о заказе {} отправлено пользователю", dto.getOrderId());
        System.out.println("Ваш заказ с номером " + dto.getOrderId() + " доставлен в пункт выдачи");
    }
}
