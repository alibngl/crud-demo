package com.crudDemo.crudDemo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum UserRoleEnum implements GrantedAuthority {
    USER("USER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN");

    private final String value;

    UserRoleEnum(String value) {
        this.value = value;
    }

    @JsonCreator
    public static UserRoleEnum fromValue(String value) {
        for (UserRoleEnum role : values()) {
            if (role.name().equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role value: " + value + ". Allowed values are: USER, MANAGER, ADMIN.");
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + value;
    }
}