package com.crudDemo.crudDemo.controller;

import com.crudDemo.crudDemo.model.Employee;
import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.model.dto.EmployeeDTO;
import com.crudDemo.crudDemo.model.dto.UserDTO;
import com.crudDemo.crudDemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return ResponseEntity.notFound().build();
        }
        EmployeeDTO employeeDTO = EmployeeDTO.mapToDTO(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.findAll()
                .stream()
                .map(EmployeeDTO::mapToDTO)
                .toList();
        return !employees.isEmpty() ? employees : null;
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

