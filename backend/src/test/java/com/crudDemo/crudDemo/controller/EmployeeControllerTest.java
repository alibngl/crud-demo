package com.crudDemo.crudDemo.controller;

import com.crudDemo.crudDemo.model.Employee;
import com.crudDemo.crudDemo.model.Location;
import com.crudDemo.crudDemo.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ActiveProfiles("test")
public class EmployeeControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private EmployeeController employeeController;

    @Test
    void testRegisterEmployee_Success() throws Exception {
        Location location = new Location();
        location.setLocation("aaa");

        User user = new User();
        user.setEmail("aaa@aaa");
        user.setEnabled(true);
        user.setPassword("aaa123");
        user.setUsername("aaa");

        Employee employee = new Employee();
        employee.setFirstName("aaaa");
        employee.setLocation(location);
        employee.setManager(true);
        employee.setSurName("bbbb");
        employee.setTckn("11111111111");
        employee.setUser(user);

        String content = (new ObjectMapper()).writeValueAsString(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Employee registered successfully."));
    }

    @Test
    void testRegisterEmployee_Failure() throws Exception {
        Location location = new Location();
        location.setLocation("aaa");

        User user = new User();
        user.setEmail("aaa@aaa");
        user.setEnabled(true);
        user.setPassword("aaa123");
        user.setUsername("aaa");

        Employee employee = new Employee();
        employee.setLocation(location);
        employee.setManager(true);
        employee.setSurName("bbbb");
        employee.setTckn("11111111111");
        employee.setUser(user);

        String content = (new ObjectMapper()).writeValueAsString(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/employees/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockMvcBuilders.standaloneSetup(employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("firstname or surname cannot be null"));
    }
}
