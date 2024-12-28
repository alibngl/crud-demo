package com.crudDemo.crudDemo.controller;

import com.crudDemo.crudDemo.model.User;
import com.crudDemo.crudDemo.model.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void registerUser_Success() throws Exception {

        User user = new User();
        user.setEmail("aaa@aaa");
        user.setEnabled(true);
        user.setPassword("aaa123");
        user.setUsername("aaa");

        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully."));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void registerUser_Failure() throws Exception {

        User user = new User();
        user.setEmail("aaa@aaa");
        user.setEnabled(true);
        user.setPassword("");
        user.setUsername("aaa");

        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("password cannot be null"));
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void updateUser_Success() throws Exception {
        UserDTO updatedUser = new UserDTO(1L, "aaa", "aaa@aaa", true, new HashSet<>(), null);
        User user = new User();
        user.setEmail("aaa@aaa");
        user.setEnabled(true);
        user.setPassword("aaa123");
        user.setUsername("aaa");
        userController.registerUser(user);
        ResponseEntity<String> actualUpdateUserResult = userController.updateUser(1L, updatedUser);

        HttpStatusCode statusCode = actualUpdateUserResult.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
        assertEquals(200, statusCode.value());
        assertTrue(actualUpdateUserResult.hasBody());
        assertEquals("User updated successfully.", actualUpdateUserResult.getBody());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void updateUser_Failure() throws Exception {
        UserDTO updatedUser = new UserDTO(1L, "aaa", "aaa@aaa", true, new HashSet<>(), null);
        User user = new User();
        user.setEmail("aaa@aaa");
        user.setEnabled(true);
        user.setPassword("aaa123");
        user.setUsername("aaa");
        userController.registerUser(user);
        ResponseEntity<String> actualUpdateUserResult = userController.updateUser(2L, updatedUser);

        HttpStatusCode statusCode = actualUpdateUserResult.getStatusCode();
        assertInstanceOf(HttpStatus.class, statusCode);
        assertEquals("User updated failed.", actualUpdateUserResult.getBody());
        assertEquals(500, actualUpdateUserResult.getStatusCode().value());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualUpdateUserResult.hasBody());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void deleteUser_Success() throws Exception {
        User user = new User();
        user.setEmail("aaa@aaa");
        user.setEnabled(true);
        user.setPassword("aaa123");
        user.setUsername("aaa");
        userController.registerUser(user);
        ResponseEntity<String> actualUpdateUserResult = userController.deleteUser(1L);

        HttpStatusCode statusCode = actualUpdateUserResult.getStatusCode();
        assertEquals(HttpStatus.OK, statusCode);
        assertEquals(200, statusCode.value());
        assertTrue(actualUpdateUserResult.hasBody());
        assertEquals("User deleted successfully.", actualUpdateUserResult.getBody());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void deleteUser_Failure() {
        User user = new User();
        user.setEmail("aaa@aaa");
        user.setEnabled(true);
        user.setPassword("aaa123");
        user.setUsername("aaa");
        userController.registerUser(user);
        ResponseEntity<String> actualDeleteUserResult = userController.deleteUser(2L);

        HttpStatusCode statusCode = actualDeleteUserResult.getStatusCode();
        assertInstanceOf(HttpStatus.class, statusCode);
        assertEquals("user deletion failed.", actualDeleteUserResult.getBody());
        assertEquals(500, actualDeleteUserResult.getStatusCode().value());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, statusCode);
        assertTrue(actualDeleteUserResult.hasBody());
        assertTrue(actualDeleteUserResult.getHeaders().isEmpty());
    }

    @Test
    @WithMockUser(username = "test_user", roles = {"ADMIN"})
    void allUsers_Success() throws Exception {
        userController.getAllUsers();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
