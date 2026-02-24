package com.example.dto;

import com.example.model.Department;
import jakarta.persistence.OneToOne;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class EmployeeResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private BigDecimal salary;
    private Department department;
}
