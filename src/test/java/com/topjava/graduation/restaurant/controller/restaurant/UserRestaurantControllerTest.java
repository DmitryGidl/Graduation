package com.topjava.graduation.restaurant.controller.restaurant;

import com.topjava.graduation.restaurant.ControllerTestConfig;
import com.topjava.graduation.restaurant.service.RestaurantService;
import com.topjava.graduation.restaurant.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.getResponseRestaurants;
import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.getRestaurantResponseDominos;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.USER_USERNAME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserRestaurantController.class)
@Import(ControllerTestConfig.class)
@WithUserDetails(USER_USERNAME)
class UserRestaurantControllerTest {

    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    RestaurantService restaurantService;

    @Autowired
    public UserRestaurantControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    @Test
    void getAllRestaurants() throws Exception {
        var restaurantResponseList = getResponseRestaurants();

        Mockito.when(restaurantService.getAll()).thenReturn(restaurantResponseList);

        var mockRequest = get("/restaurants");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].name", is("Adriano's pizza")));
    }

    @Test
    void getRestaurantById() throws Exception {

        Mockito.when(restaurantService.getById(1)).thenReturn(getRestaurantResponseDominos());

        var mockRequest = get("/restaurants/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dominos Pizza")))
                .andExpect(jsonPath("$.votesToday", is(1)));
    }
}


