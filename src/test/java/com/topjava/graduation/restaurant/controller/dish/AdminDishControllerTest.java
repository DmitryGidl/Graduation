package com.topjava.graduation.restaurant.controller.dish;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topjava.graduation.restaurant.ControllerTestConfig;
import com.topjava.graduation.restaurant.service.DishService;
import com.topjava.graduation.restaurant.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static com.topjava.graduation.restaurant.test_data.DishTestModel.*;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.ADMIN_USERNAME;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminDishController.class)
@Import(ControllerTestConfig.class)
@WithUserDetails(ADMIN_USERNAME)
class AdminDishControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @MockBean
    DishService dishService;
    @MockBean
    UserService userService;

    @Autowired
    public AdminDishControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void createDish() throws Exception {
        var dishDeepFried = getDishCreationDeepfried();
        when(dishService.createDish(dishDeepFried)).thenReturn(getDishResponseDeepfried());

        var mockRequest =
                post("/admin/dishes/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(dishDeepFried));

        mockMvc.perform(mockRequest)
                .andExpect(jsonPath("$.name", is("Assorted Deep fried")));
    }

    @Test
    void updateDish() throws Exception {
        var dishCreationMargarita = getDishCreationMargarita();
        var dishResponseMargarita = getDishResponseMargarita();
        when(dishService.update(3, dishCreationMargarita)).thenReturn(dishResponseMargarita);

        var mockRequest =
                put("/admin/dishes/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(dishCreationMargarita));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Pizza Margherita")));
    }

    @Test
    void deleteDish() throws Exception {
        mockMvc.perform(delete("/admin/dishes/5"));

        Mockito.verify(dishService, times(1))
                .delete(5);
    }


}
