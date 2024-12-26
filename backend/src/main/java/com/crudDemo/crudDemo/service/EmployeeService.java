package com.crudDemo.crudDemo.service;

import com.crudDemo.crudDemo.model.Employee;

import java.util.List;

public interface EmployeeService {

    void save(Employee employeeTable);

    Employee findById(Long id);

    List<Employee> findAll();

    void deleteEmployee(Long id);
}
