package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long productId;

    @NotNull
    private String name;
    private String description;
    @NotNull
    @Positive
    private BigDecimal price;
    @Positive
    private Integer quantityInStock;
}
