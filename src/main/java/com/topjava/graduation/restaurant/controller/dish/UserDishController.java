package com.topjava.graduation.restaurant.controller.dish;

import com.topjava.graduation.restaurant.dto.DishResponseDTO;
import com.topjava.graduation.restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dish")
public class UserDishController {

    DishService dishService;

    @Autowired
    public UserDishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<DishResponseDTO> getAllToday() {
        return dishService.getAllToday();
    }

    @GetMapping("/{id}")
    public DishResponseDTO getOne(@PathVariable int id) {
        return dishService.getOne(id);
    }

    @GetMapping("/history")
    public List<DishResponseDTO> getDishHistory() {
        return dishService.getAll();
    }

    @GetMapping("/history/{id}")
    public List<DishResponseDTO> getDishHistoryByRestaurant(@PathVariable int id) {
        return dishService.getDishHistoryByRestaurant(id);
    }

}
