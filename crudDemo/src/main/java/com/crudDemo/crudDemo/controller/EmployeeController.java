package com.crudDemo.crudDemo.controller;

import com.crudDemo.crudDemo.model.Employee;
import com.crudDemo.crudDemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    public ResponseEntity<String> registerEmployee(@RequestBody Employee employee) {
        employeeService.save(employee);
        return ResponseEntity.ok("Employee registered successfully.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employeeTable) {
        employeeService.save(employeeTable);
        return ResponseEntity.ok("Employee updated successfully.");
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        List<Employee> employeeTables = employeeService.findAll();
        return !employeeTables.isEmpty() ? employeeTables : null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (employeeService.findById(id) == null) {
            throw new IllegalArgumentException("Employee with id " + id + " does not exist.");
        }
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }
}

