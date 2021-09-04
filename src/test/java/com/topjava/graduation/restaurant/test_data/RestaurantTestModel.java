package com.topjava.graduation.restaurant.test_data;

import com.topjava.graduation.restaurant.dto.RestaurantCreationDTO;
import com.topjava.graduation.restaurant.dto.RestaurantResponseDTO;
import com.topjava.graduation.restaurant.entity.Dish;
import com.topjava.graduation.restaurant.entity.Restaurant;
import com.topjava.graduation.restaurant.entity.Vote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.topjava.graduation.restaurant.test_data.DishTestModel.*;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.getAdminUser;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.getBasicUser;

public abstract class RestaurantTestModel extends TestBaseData {

    // Restaurants
    public static Restaurant getRestaurantDominos() {
        return new Restaurant(1, "Dominos Pizza", "30 Queen Street",
                getDishesDominos(), new ArrayList<>(Arrays.asList(
                new Vote(1, null, todayDate, getBasicUser()),
                new Vote(3, null, yesterdayDate, getAdminUser()))));
    }

    public static Restaurant getRestaurantBangalore() {
        return new Restaurant(2, "Bangalore Spices", "87 Stanley Road",
                getDishesBangalore(), new ArrayList<>(List.of(
                new Vote(2, null, todayDate, getBasicUser()))));
    }

    public static Restaurant getRestaurantAdriano() {
        return new Restaurant(3, "Adriano's pizza", "72 Mill Lane",
                new ArrayList<>(), new ArrayList<>());
    }

    public static List<Dish> getDishesDominos() {
        return new ArrayList<>(Arrays.asList(getDishPeperoni(), getDishMargarita()));
    }

    private static List<Dish> getDishesBangalore() {
        return new ArrayList<>(Arrays.asList(getDishDeepfried(), getDishVegibox()));
    }

    public static List<Restaurant> getRestaurants() {
        return new ArrayList<>(Arrays.asList(getRestaurantDominos(), getRestaurantBangalore(), getRestaurantAdriano()));
    }

    // RestaurantCreationDTOs
    public static RestaurantCreationDTO getRestaurantCreationDominos() {
        return new RestaurantCreationDTO(
                "Dominos Pizza", "30 Queen Street");
    }

    public static RestaurantCreationDTO getRestaurantCreationBangalore() {
        return new RestaurantCreationDTO("Bangalore Spices", "87 Stanley Road");
    }

    public static RestaurantCreationDTO getRestaurantCreationAdriano() {
        return new RestaurantCreationDTO("Adriano's pizza", "72 Mill Lane");
    }


    // RestaurantResponseDTOs
    public static RestaurantResponseDTO getRestaurantResponseDominos() {
        return new RestaurantResponseDTO(1, "Dominos Pizza",
                "30 Queen Street", 1);
    }

    public static RestaurantResponseDTO getRestaurantResponseBangalore() {
        return new RestaurantResponseDTO(2, "Bangalore Spices",
                "87 Stanley Road", 1);

    }

    public static RestaurantResponseDTO getRestaurantResponseAdriano() {
        return new RestaurantResponseDTO(3, "Adriano's pizza",
                "72 Mill Lane", 0);
    }

    public static List<RestaurantResponseDTO> getResponseRestaurants() {
        return new ArrayList<>(Arrays.asList(getRestaurantResponseDominos(),
                getRestaurantResponseBangalore(), getRestaurantResponseAdriano()));
    }
}
