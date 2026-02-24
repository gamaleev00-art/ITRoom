package com.example.service;

import com.example.dto.EmployeeRequestDTO;
import com.example.dto.EmployeeDTO;
import com.example.exception.DepartmentNotFoundException;
import com.example.exception.EmployeeNotFoundException;
import com.example.model.Department;
import com.example.model.Employee;
import com.example.projection.EmployeeProjection;
import com.example.repository.DepartmentRepository;
import com.example.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional(readOnly = true)
    public EmployeeProjection getEmployee(Long id){
        return employeeRepository.findProjectedById(id)
                .orElseThrow(()->
                new EmployeeNotFoundException("Employee with id: " + id + " not found"));
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeRequestDTO dto) {
        Department department = findDepartmentOrThrow(dto.getDepartmentName());

        Employee employee = new Employee();
        employee.setDepartment(department);
        employee.setPosition(dto.getPosition());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employeeRepository.save(employee);
        return employeeResponseDTOMapper(employee);
    }
    @Transactional
    public void updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee employee = findEmployeeOrThrow(id);
        if (dto.getFirstName() != null) {
            employee.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            employee.setLastName(dto.getLastName());
        }
        if (dto.getDepartmentName() != null) {
            Department department = findDepartmentOrThrow(dto.getDepartmentName());
            employee.setDepartment(department);
        }
        if (dto.getPosition() != null) {
            employee.setPosition(dto.getPosition());
        }
        if (dto.getSalary() != null) {
            employee.setSalary(dto.getSalary());
        }
    }

    @Transactional
    public void deleteEmployee(Long id) {
        findEmployeeOrThrow(id);
        employeeRepository.deleteById(id);
    }

    private Employee findEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(()->
                new EmployeeNotFoundException("Employee with id: " + id + " not found"));
    }

    private Department findDepartmentOrThrow(String departmentName) {
        return departmentRepository.findByName(departmentName).orElseThrow(()->
                new DepartmentNotFoundException("Department with name: " + departmentName + " not found"));
    }

    private EmployeeDTO employeeResponseDTOMapper(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .position(employee.getPosition())
                .salary(employee.getSalary())
                .department(employee.getDepartment())
                .build();
    }
}
