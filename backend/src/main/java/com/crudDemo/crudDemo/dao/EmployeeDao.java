package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.Employee;

import java.util.List;

public interface EmployeeDao extends AbstractDao {

    Employee findById(Long id);

    List<Employee> findAll();
}
