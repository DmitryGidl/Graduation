package com.topjava.graduation.restaurant.controller.restaurant;

import com.topjava.graduation.restaurant.dto.RestaurantResponseDTO;
import com.topjava.graduation.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class UserRestaurantController {

    RestaurantService restaurantService;

    @Autowired
    public UserRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantResponseDTO> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    public RestaurantResponseDTO getOne(@PathVariable int id) {
        return restaurantService.getById(id);
    }


}
