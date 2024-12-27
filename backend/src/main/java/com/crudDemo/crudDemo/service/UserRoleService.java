package com.crudDemo.crudDemo.service;

import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.model.UserRole;

import java.util.List;

public interface UserRoleService {

    void save(UserRole userRole);

    void deleteUserRole(Long id);

    void saveAll(List<UserRole> userRoles);
}
