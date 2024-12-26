package com.crudDemo.crudDemo.model.dto;

import com.crudDemo.crudDemo.model.User;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private Set<String> roles;
    private EmployeeDTO employee;

    public UserDTO(Long id, String username, String email, boolean enabled, Set<String> roles, EmployeeDTO employee) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
        this.employee = employee;
    }

    public static UserDTO mapToDTO(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Set<String> roles = user.getUserRoles().stream()
                .map(userRoleTable -> userRoleTable.getUserRoleEnum().name())
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled(),
                roles,
                EmployeeDTO.mapToDTO(user.getEmployee())
        );
    }
}
