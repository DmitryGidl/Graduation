package com.topjava.graduation.restaurant.repository;

import com.topjava.graduation.restaurant.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
     List<Dish> getAllByRestaurantId(int restaurantId);
     List<Dish> getAllByDateAdded(LocalDate localDate);
     Integer deleteDishById(int dishId);
     Optional<Dish> findByNameAndRestaurantIdAndDateAdded(String name, int restaurantId, LocalDate dateAdded);
}
