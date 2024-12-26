package com.crudDemo.crudDemo.controller;

import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.model.dto.UserResponseDTO;
import com.crudDemo.crudDemo.security.JwtUtil;
import com.crudDemo.crudDemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtilService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        User user = userService.getByUsername(loginRequest.getUsername());

        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            List<String> roles = user.getUserRoles().stream().map(userRoleTable -> userRoleTable.getUserRoleEnum().getValue()).toList();
            String token = jwtUtilService.generateToken(user.getUsername(), roles);

            UserResponseDTO responseDTO = new UserResponseDTO();
            responseDTO.setToken(token);
            responseDTO.setUsername(user.getUsername());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setEnabled(user.isEnabled());
            responseDTO.setRoles(user.getUserRoles()
                    .stream()
                    .map(role -> role.getUserRoleEnum().name())
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(responseDTO);
        } else {
            LOG.error("Hatalı kullanıcı adı veya şifre: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hatalı kullanıcı adı veya şifre");
        }
    }
}
