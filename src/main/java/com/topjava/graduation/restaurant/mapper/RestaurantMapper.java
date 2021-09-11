package com.topjava.graduation.restaurant.mapper;

import com.topjava.graduation.restaurant.dto.RestaurantCreationDTO;
import com.topjava.graduation.restaurant.dto.RestaurantResponseDTO;
import com.topjava.graduation.restaurant.entity.Restaurant;
import com.topjava.graduation.restaurant.entity.Vote;

import java.time.LocalDate;
import java.util.List;

public abstract class RestaurantMapper {

    private RestaurantMapper() {
    }

    public static RestaurantResponseDTO toRestaurantDto(Restaurant restaurant, int voteCount) {

        return new RestaurantResponseDTO(restaurant.getId(), restaurant.getName(),
                restaurant.getAddress(), voteCount);
    }

    public static Restaurant toRestaurant(RestaurantCreationDTO restaurantCreationDTO) {
        return new Restaurant(restaurantCreationDTO.getName(), restaurantCreationDTO.getAddress());
    }
}
