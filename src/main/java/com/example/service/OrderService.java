package com.example.service;


import com.example.exception.OrderNotFoundException;
import com.example.model.Order;
import com.example.model.Product;
import com.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Product> getProducts(Long id){
        return orderOrElseThrow(id).getProducts();
    }

    public BigDecimal getTotalPrice(Long id){
        return orderOrElseThrow(id)
                .getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order orderOrElseThrow(Long id){
        return orderRepository.findById(id).orElseThrow(()->
                new OrderNotFoundException("Order not found"));
    }
}
