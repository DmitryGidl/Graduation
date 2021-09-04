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

    public static RestaurantResponseDTO toRestaurantDto(Restaurant restaurant) {

        return new RestaurantResponseDTO(restaurant.getId(), restaurant.getName(),
                restaurant.getAddress(), getRestaurantVoteCount(restaurant));
    }

    public static Restaurant toRestaurant(RestaurantCreationDTO restaurantCreationDTO) {
        return new Restaurant(restaurantCreationDTO.getName(), restaurantCreationDTO.getAddress());
    }
    public static long getRestaurantVoteCount(Restaurant restaurant) {
        var votes = restaurant.getVotes();
        if (votes == null) return 0;
        return  votes.stream().filter(vote -> vote.getVoteDate().equals(LocalDate.now())).count();

    }
}
