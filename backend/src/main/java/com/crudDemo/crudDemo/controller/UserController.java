package com.crudDemo.crudDemo.controller;

import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.model.UserRole;
import com.crudDemo.crudDemo.model.dto.UserDTO;
import com.crudDemo.crudDemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.save(user);
            LOG.info("User registered successfully: {}", user.getUsername());
            return ResponseEntity.ok("User registered successfully.");
        } catch (IllegalArgumentException e) {
            LOG.error("Validation failed: {}", e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            LOG.error("User not saved", e);
            return ResponseEntity.status(500).body("User registration failed.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            userService.updateUser(id, userDTO);
            LOG.info("User updated successfully: {}", userDTO.getUsername());
            return ResponseEntity.status(200).body("User updated successfully.");
        } catch (Exception e) {
            LOG.error("User not updated", e);
            return ResponseEntity.status(500).body("User updated failed.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = UserDTO.mapToDTO(user);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserDTO::mapToDTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            if (userService.getById(id) == null) {
                throw new IllegalArgumentException("User with id " + id + " does not exist.");
            }
            userService.deleteUser(id);
            LOG.info("User with ID {} deleted successfully.", id);
            return ResponseEntity.status(200).body("User deleted successfully.");
        } catch (Exception e) {
            LOG.error("user not deleted {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(500).body("user deletion failed.");
        }
    }

    @PostMapping("/{userId}/roles")
    public ResponseEntity<String> addRoleToUser(@PathVariable Long userId, @RequestBody UserRole role) {
        User user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        role.setUser(user);
        if (user.getUserRoles() == null) {
            user.setUserRoles(new ArrayList<>());
        }
        user.getUserRoles().add(role);
        userService.save(user);
        return ResponseEntity.ok("Role added to user successfully.");
    }

    @DeleteMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<String> removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        User user = userService.getById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<UserRole> roles = user.getUserRoles();
        if (roles != null) {
            roles.removeIf(role -> role.getId().equals(roleId));
            userService.save(user);
        }
        return ResponseEntity.ok("Role removed from user successfully.");
    }
}

