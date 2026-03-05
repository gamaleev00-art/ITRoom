package orders.controller;

import dto_order.OrderDTO;
import lombok.RequiredArgsConstructor;
import orders.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ButtonCreateOrderController {

    private final OrderService orderService;


    @GetMapping
    public String createOrder() {
        OrderDTO orderDTO = orderService.createOrder();
        return "Order created " + orderDTO.toString();
    }
}
