package com.topjava.graduation.restaurant.controller.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topjava.graduation.restaurant.dto.UserDTO;
import com.topjava.graduation.restaurant.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
class RegistrationTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Autowired
    public RegistrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void createUser() throws Exception {
        UserDTO userDTO = new UserDTO("someUsername", "testemail@gmail.com", "somepassword");

        var mockRequest = post(
                "/user/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(userDTO));

        mockMvc.perform(mockRequest).andExpect(status().isCreated());

        Mockito.verify(userService, Mockito.times(1)).registerNewUserAccount(userDTO);
    }

}

