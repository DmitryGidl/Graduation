package com.topjava.graduation.restaurant.controller.dish;

import com.topjava.graduation.restaurant.ControllerTestConfig;
import com.topjava.graduation.restaurant.dto.DishResponseDTO;
import com.topjava.graduation.restaurant.service.DishService;
import com.topjava.graduation.restaurant.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.topjava.graduation.restaurant.test_data.DishTestModel.getResponseDishes;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.USER_USERNAME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserDishController.class)
@Import(ControllerTestConfig.class)
@WithUserDetails(USER_USERNAME)
public class UserDishControllerTest {

    MockMvc mockMvc;

    @MockBean
    DishService dishService;
    @MockBean
    UserService userService;

    @Autowired
    public UserDishControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void getAllDishToday() throws Exception {
        List<DishResponseDTO> dishResponseList = getResponseDishes();

        Mockito.when(dishService.getAllToday()).thenReturn(dishResponseList);

        var mockRequest = get("/dish");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[1].name", is("Pizza Margherita")));
    }

    @Test
    void getAllDishHistory() throws Exception {
        List<DishResponseDTO> dishResponseList = getResponseDishes();

        Mockito.when(dishService.getAll()).thenReturn(dishResponseList);

        var mockRequest = get("/dish/history");
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].name", is("Vegi BOX")));
    }

    @Test
    void getAllDishHistoryByRestaurant() throws Exception {
        List<DishResponseDTO> dishResponseList = getResponseDishes();

        Mockito.when(dishService.getDishHistoryByRestaurant(4)).thenReturn(dishResponseList);

        var mockRequest = get("/dish/history/4");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].name", is("Vegi BOX")));

    }
}

