package com.example.projection;

import com.example.model.Employee;
import org.springframework.beans.factory.annotation.Value;


public interface EmployeeProjection {
    String getFirstName();

    String getLastName();

    String getPosition();

    String getDepartmentName();

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
