package com.crudDemo.crudDemo.dao;

import com.crudDemo.crudDemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user_table WHERE id IS NOT NULL", nativeQuery = true)
    List<User> findAllUsers();
}

