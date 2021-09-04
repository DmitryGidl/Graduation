package com.topjava.graduation.restaurant.controller.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.topjava.graduation.restaurant.ControllerTestConfig;
import com.topjava.graduation.restaurant.service.RestaurantService;
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

import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.*;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.ADMIN_USERNAME;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AdminRestaurantController.class)
@Import(ControllerTestConfig.class)
@WithUserDetails(ADMIN_USERNAME)
public class AdminRestaurantControllerTest {

    MockMvc mockMvc;
    ObjectMapper objectMapper;

    @Autowired
    public AdminRestaurantControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @MockBean
    UserService userService;
    @MockBean
    RestaurantService restaurantService;

    @Test
    void createRestaurant() throws Exception {

        var restaurantCreationDominos = getRestaurantCreationDominos();
        when(restaurantService.create(restaurantCreationDominos)).thenReturn(getRestaurantResponseDominos());

        var mockRequest = post(
                "/admin/restaurant/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(restaurantCreationDominos));

        mockMvc.perform(mockRequest)
                .andExpect(jsonPath("$.name", is("Dominos Pizza")));
    }

    @Test
    void updateRestaurant() throws Exception {
        var restaurantCreationAdriano = getRestaurantCreationAdriano();

        mockMvc.perform(put("/admin/restaurant/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(restaurantCreationAdriano)));

        Mockito.verify(restaurantService, times(1))
                .update(2, restaurantCreationAdriano);
    }

    @Test
    void deleteRestaurant() throws Exception {
        mockMvc.perform(delete("/admin/restaurant/3"));

        Mockito.verify(restaurantService, times(1)).deleteById(3);
    }

}



