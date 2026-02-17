package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    public Order createOrder(String json) {
        try {
            Order order = objectMapper.readValue(json, Order.class);
            return orderRepository.save(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка парсинга заказа");
        }
    }

    public String getOrderInfo(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(()->
                new RuntimeException("Заказ не найден")
        );
        try {
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка создания json");
        }
    }
}