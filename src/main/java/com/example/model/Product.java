package com.example.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
