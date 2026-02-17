package com.example.controller;

import com.example.model.Order;
import com.example.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/{id}")
    public String getOrder(@PathVariable UUID id) {
        return orderService.getOrderInfo(id);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody String json) {
        return ResponseEntity.ok(orderService.createOrder(json));
    }
}
