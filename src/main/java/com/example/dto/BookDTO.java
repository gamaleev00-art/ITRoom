package com.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BookDTO(
        Long id,
        @NotNull(message = "Название книги не может быть пустым") String name,
        Integer volume,
        @NotNull @Positive BigDecimal price
) {
}
