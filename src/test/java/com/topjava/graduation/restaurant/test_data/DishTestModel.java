package com.topjava.graduation.restaurant.test_data;

import com.topjava.graduation.restaurant.dto.DishCreationDTO;
import com.topjava.graduation.restaurant.dto.DishResponseDTO;
import com.topjava.graduation.restaurant.entity.Dish;
import com.topjava.graduation.restaurant.entity.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DishTestModel extends TestBaseData {

    // DishCreationDTOs
    public static DishCreationDTO getDishCreationPepperoni() {
        return new DishCreationDTO("Pizza Pepperoni", 150, 1, todayDate);
    }

    public static DishCreationDTO getDishCreationMargarita() {
        return new DishCreationDTO("Pizza Margherita", 150, 1, todayDate);
    }

    public static DishCreationDTO getDishCreationDeepfried() {
        return new DishCreationDTO("Assorted Deep fried", 289, 2, todayDate);
    }

    public static DishCreationDTO getDishCreationVegibox() {
        return new DishCreationDTO("Vegi BOX", 355, 2, todayDate);
    }

    public static List<DishCreationDTO> getCreationDishes() {
        return new ArrayList<>(Arrays.asList(getDishCreationPepperoni(),
                getDishCreationMargarita(), getDishCreationDeepfried(), getDishCreationVegibox()));
    }

    // DishResponseDTOs
    public static DishResponseDTO getDishResponsePepperoni() {
        return new DishResponseDTO(
                1, "Pizza Pepperoni", 150, "Dominos Pizza",
                "30 Queen Street", todayDate, 1);
    }

    public static DishResponseDTO getDishResponseMargarita() {
        return new DishResponseDTO(
                2, "Pizza Margherita", 137, "Dominos Pizza",
                "30 Queen Street", tomorrowDate, 1);
    }

    public static DishResponseDTO getDishResponseDeepfried() {
        return new DishResponseDTO(
                3, "Assorted Deep fried", 289, "Bangalore Spices",
                "87 Stanley Road", todayDate, 2);
    }

    public static DishResponseDTO getDishResponseVegibox() {
        return new DishResponseDTO(
                4, "Vegi BOX", 355, "Bangalore Spices",
                "87 Stanley Road", yesterdayDate, 2);
    }

    public static List<DishResponseDTO> getResponseDishes() {
        return new ArrayList<>(Arrays.asList(getDishResponsePepperoni(),
                getDishResponseMargarita(), getDishResponseDeepfried(), getDishResponseVegibox()));
    }


    // Dishes
    public static Dish getDishPeperoni() {
        return new Dish(1, "Pizza Pepperoni", 150,
                todayDate, new Restaurant(1, "Dominos Pizza",
                "30 Queen Street", null, null));
    }

    public static Dish getDishMargarita() {
        return new Dish(2, "Pizza Margherita", 137,
                tomorrowDate, new Restaurant(1, "Dominos Pizza",
                "30 Queen Street", null, null));
    }

    public static Dish getDishDeepfried() {
        return new Dish(3, "Assorted Deep fried", 289,
                todayDate, new Restaurant(2, "Bangalore Spices",
                "87 Stanley Road", null, null));
    }

    public static Dish getDishVegibox() {
        return new Dish(4, "Vegi BOX", 355,
                yesterdayDate, new Restaurant(2, "Bangalore Spices",
                "87 Stanley Road", null, null));
    }

    public static List<Dish> getDishes() {
        return new ArrayList<>(Arrays.asList(getDishPeperoni(), getDishMargarita(),
                getDishDeepfried(), getDishVegibox()));
    }
}
