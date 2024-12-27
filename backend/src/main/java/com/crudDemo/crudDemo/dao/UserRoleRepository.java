package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
