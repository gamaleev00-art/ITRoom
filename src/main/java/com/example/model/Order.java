package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @NotNull
    private String shippingAddress;
    @NotNull
    @Positive
    private BigDecimal totalPrice;
    private String orderStatus;
    @Column(updatable = false)
    private LocalDateTime orderDate;

    @OneToMany
    private Set<Product> products;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @PrePersist
    public void prePersist() {
        if (orderDate== null) orderDate = LocalDateTime.now();
    }
}
