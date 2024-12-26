package com.crudDemo.crudDemo.model.dto;

import com.crudDemo.crudDemo.model.Employee;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String surName;
    private boolean manager;
    private String location;

    public EmployeeDTO(Long id, String firstName, String surName, boolean manager, String location) {
        this.id = id;
        this.firstName = firstName;
        this.surName = surName;
        this.manager = manager;
        this.location = location;
    }

    public static EmployeeDTO mapToDTO(Employee employee) {
        if (employee == null) {
            return null;
        }

        return new EmployeeDTO(
                employee.getId(),
                employee.getFirstName(),
                employee.getSurName(),
                employee.isManager(),
                employee.getLocation() != null ? employee.getLocation().getLocation() : null
        );
    }
}
