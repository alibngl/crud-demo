package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.User;

import java.util.List;

public interface UserDao extends AbstractDao {

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();
}
