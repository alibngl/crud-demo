package com.crudDemo.crudDemo.service.impl;

import com.crudDemo.crudDemo.dao.EmployeeRepository;
import com.crudDemo.crudDemo.model.Employee;
import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.service.EmployeeService;
import com.crudDemo.crudDemo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    @Transactional
    @Override
    public void save(Employee employeeTable) {
        if (employeeTable.getUser().getId() != null) {
            User userTable = userService.getById(employeeTable.getUser().getId());
            if (userTable != null) {
                employeeTable.setUser(userTable);
                userTable.setEmployee(employeeTable);
            }
        }
        employeeRepository.save(employeeTable);
    }

    @Override
    public Employee findById(Long id) {
        Employee employeeTable = employeeRepository.findById(id).get();
        if (employeeTable.getId() == null) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        return employeeTable;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }


    @Transactional
    @Override
    public void deleteEmployee(Long id) {
        Employee deleteEmployee = employeeRepository.findById(id).get();
        if (deleteEmployee.getId() != null) {
            employeeRepository.delete(deleteEmployee);
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }
}
