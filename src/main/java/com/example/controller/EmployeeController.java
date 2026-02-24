package com.example.controller;

import com.example.dto.EmployeeRequestDTO;
import com.example.dto.EmployeeDTO;
import com.example.projection.EmployeeProjection;
import com.example.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeProjection getFullName(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateEmployee(@PathVariable Long id,
                               @RequestBody EmployeeRequestDTO dto) {
        employeeService.updateEmployee(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
