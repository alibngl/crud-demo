package com.crudDemo.crudDemo.service;

import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {

    void save(User user);

    void updateUser(Long id, UserDTO userDTO);

    User getById(Long id);

    User getByUsername(String username);

    List<User> getAllUsers();

    void deleteUser(Long id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
