package com.example.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookRequestDTO {
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull @Positive
    private Integer publicationYear;
}
