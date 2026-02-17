package com.example.dto;


import jakarta.validation.constraints.NotNull;

public record AuthorDTO(
        @NotNull(message = "Имя не может быть пустым") String firstName,
        String lastName
) {
}
