package com.topjava.graduation.restaurant.repository;

import com.topjava.graduation.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Integer deleteRestaurantById(int id);
    Optional<Restaurant> findByNameAndAddress(String name, String address);
}
