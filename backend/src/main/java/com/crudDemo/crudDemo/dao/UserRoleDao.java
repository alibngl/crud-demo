package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.UserRole;

public interface UserRoleDao extends AbstractDao{
    UserRole findRolesByUserId(Long userId);

    void deleteUserRole(Long userRoleId);
}
