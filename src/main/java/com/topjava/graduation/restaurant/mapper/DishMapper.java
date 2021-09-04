package com.topjava.graduation.restaurant.mapper;

import com.topjava.graduation.restaurant.dto.DishCreationDTO;
import com.topjava.graduation.restaurant.dto.DishResponseDTO;
import com.topjava.graduation.restaurant.entity.Dish;
import com.topjava.graduation.restaurant.entity.Restaurant;

public abstract class DishMapper {

    private DishMapper() {
    }

    public static DishResponseDTO toDishResponseDTO(Dish dish) {
        var restaurant = dish.getRestaurant();
        return new DishResponseDTO(dish.getId(), dish.getName(), dish.getPrice(),
                restaurant.getName(), restaurant.getAddress(), dish.getDateAdded(), restaurant.getId());
    }
    public static Dish toDish(DishCreationDTO dishCreationDto, Restaurant restaurant) {
        return new Dish(dishCreationDto.getName(),
                dishCreationDto.getPrice(), dishCreationDto.getDateAdded(), restaurant);
    }
}
