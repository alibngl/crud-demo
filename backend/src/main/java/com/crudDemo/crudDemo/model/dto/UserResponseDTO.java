package com.crudDemo.crudDemo.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {

    private String username;

    private String email;

    private boolean enabled;

    private String token;

    private List<String> roles;
}

