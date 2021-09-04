package com.topjava.graduation.restaurant.test_data;

import com.topjava.graduation.restaurant.dto.RestaurantCreationDTO;
import com.topjava.graduation.restaurant.dto.RestaurantResponseDTO;
import com.topjava.graduation.restaurant.entity.Dish;
import com.topjava.graduation.restaurant.entity.Restaurant;
import com.topjava.graduation.restaurant.entity.Vote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.topjava.graduation.restaurant.test_data.DishTestModel.*;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.getAdminUser;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.getBasicUser;

public abstract class RestaurantTestModel extends TestBaseData {

    // Restaurants
    public static Restaurant getRestaurantDominos() {
        return new Restaurant(1, "Dominos Pizza", "Басейна, 17",
                getDishesDominos(), new ArrayList<>(Arrays.asList(
                new Vote(1, null, todayDate, getBasicUser()),
                new Vote(3, null, yesterdayDate, getAdminUser()))));
    }

    public static Restaurant getRestaurantMamamia() {
        return new Restaurant(2, "Mamamia", "проспект Победы, 9Б",
                getDishesMamamia(), new ArrayList<>(List.of(
                new Vote(2, null, todayDate, getBasicUser()))));
    }

    public static Restaurant getRestaurantAdriano() {
        return new Restaurant(3, "Adriano's pizza", "Глибочицкая, 33/37",
                new ArrayList<>(), new ArrayList<>());
    }

    public static List<Dish> getDishesDominos() {
        return new ArrayList<>(Arrays.asList(getDishPeperoni(), getDishMargarita()));
    }

    private static List<Dish> getDishesMamamia() {
        return new ArrayList<>(Arrays.asList(getDishDeepfried(), getDishVegibox()));
    }

    public static List<Restaurant> getRestaurants() {
        return new ArrayList<>(Arrays.asList(getRestaurantDominos(), getRestaurantMamamia(), getRestaurantAdriano()));
    }

    // RestaurantCreationDTOs
    public static RestaurantCreationDTO getRestaurantCreationDominos() {
        return new RestaurantCreationDTO(
                "Dominos Pizza", "Басейная, 17");
    }

    public static RestaurantCreationDTO getRestaurantCreationMamamia() {
        return new RestaurantCreationDTO("Mamamia", "проспект Победы, 9Б");
    }

    public static RestaurantCreationDTO getRestaurantCreationAdriano() {
        return new RestaurantCreationDTO("Adriano's pizza", "Глибочицкая, 33/37");
    }

    public static List<RestaurantCreationDTO> getCreationRestaurants() {
        return new ArrayList<>(Arrays.asList(getRestaurantCreationDominos(),
                getRestaurantCreationMamamia()));
    }

    // RestaurantResponseDTOs
    public static RestaurantResponseDTO getRestaurantResponseDominos() {
        return new RestaurantResponseDTO(1, "Dominos Pizza",
                "Басейна, 17", 1);
    }

    public static RestaurantResponseDTO getRestaurantResponseMamamia() {
        return new RestaurantResponseDTO(2, "Mamamia",
                "проспект Победы, 9Б", 1);

    }

    public static RestaurantResponseDTO getRestaurantResponseAdriano() {
        return new RestaurantResponseDTO(3, "Adriano's pizza",
                "Глибочицкая, 33/37", 0);
    }

    public static List<RestaurantResponseDTO> getResponseRestaurants() {
        return new ArrayList<>(Arrays.asList(getRestaurantResponseDominos(),
                getRestaurantResponseMamamia(), getRestaurantResponseAdriano()));
    }
}
