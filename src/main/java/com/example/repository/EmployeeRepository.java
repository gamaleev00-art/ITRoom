package com.example.repository;

import com.example.model.Employee;
import com.example.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<EmployeeProjection> findProjectedById(Long id);
}
