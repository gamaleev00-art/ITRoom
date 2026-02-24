package com.example.controller;

import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeRequestDTO;
import com.example.exception.EmployeeNotFoundException;
import com.example.model.Department;
import com.example.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;



    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Должен возвращать 201 и созданного сотрудника при POST запросе")
    void shouldCreateEmployee() throws Exception {



        EmployeeRequestDTO request = new EmployeeRequestDTO();
        request.setFirstName("Ivan");
        request.setLastName("Ivanov");
        request.setDepartmentName("Java");
        request.setPosition("Junior");
        request.setSalary(BigDecimal.valueOf(15_000));

        Department department = new Department();
        department.setId(1L);
        department.setName(request.getDepartmentName());

        EmployeeDTO response = EmployeeDTO.builder()
                .id(1L)
                .firstName("Ivan")
                .lastName("Ivanov")
                .department(department)
                .position(request.getPosition())
                .salary(request.getSalary())
                .build();

        Mockito.when(employeeService.createEmployee(any(EmployeeRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"))
                .andExpect(jsonPath("$.position").value("Junior"))
                .andExpect(jsonPath("$.salary").value(15_000));
    }

    @Test
    @DisplayName("Должен возвращать 404, если сотрудник не найден")
    void shouldReturn404WhenNotFound() throws Exception {

        Mockito.when(employeeService.getEmployee(1L))
                .thenThrow(new EmployeeNotFoundException("Employee not found"));


        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isNotFound());
    }
}
