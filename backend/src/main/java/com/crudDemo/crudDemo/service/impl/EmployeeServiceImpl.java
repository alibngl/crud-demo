package com.crudDemo.crudDemo.service.impl;

import com.crudDemo.crudDemo.controller.UserController;
import com.crudDemo.crudDemo.dao.EmployeeDao;
import com.crudDemo.crudDemo.dao.LocationDao;
import com.crudDemo.crudDemo.model.Employee;
import com.crudDemo.crudDemo.model.Location;
import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.model.UserRole;
import com.crudDemo.crudDemo.service.EmployeeService;
import com.crudDemo.crudDemo.service.LocationService;
import com.crudDemo.crudDemo.service.UserRoleService;
import com.crudDemo.crudDemo.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LocationService locationService;

    @Transactional
    @Override
    public void save(Employee employee) {
        if (employee.getUser() != null) {
            userService.save(employee.getUser());
            User user = userService.getById(employee.getUser().getId());
            if (!employee.getUser().getUserRoles().isEmpty()) {
                List<UserRole> userRoles = user.getUserRoles();
                for (UserRole userRole : userRoles) {
                    userRole.setUser(user);
                }
                userRoleService.saveAll(userRoles);
                employee.getUser().setUserRoles(userRoles);
                user.setEmployee(employee);
            }
        }
        if (employee.getLocation() != null) {
            if (employee.getLocation().getId() != null) {
                Location location = locationService.findById(employee.getLocation().getId());
                if (location != null) {
                    employee.setLocation(location);
                } else {
                    locationService.save(employee.getLocation());
                    Location l = locationService.findById(employee.getLocation().getId());
                    employee.setLocation(l);
                }
            } else {
                locationService.save(employee.getLocation());
            }
        }
        employeeDao.save(employee);
    }

    @Override
    @Cacheable(value = "employees", key = "#id")
    public Employee findById(Long id) {
        LOG.info("Find employee by id: {}", id);
        Employee employee = employeeDao.findById(id);
        if (employee.getId() == null) {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
        return employee;
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }


    @Transactional
    @Override
    public void deleteEmployee(Long id) {
        Employee deleteEmployee = employeeDao.findById(id);
        if (deleteEmployee.getId() != null) {
            employeeDao.delete(deleteEmployee);
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }
}
