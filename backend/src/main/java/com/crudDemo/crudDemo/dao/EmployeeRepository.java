package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
