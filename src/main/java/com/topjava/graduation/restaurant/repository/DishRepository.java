package com.topjava.graduation.restaurant.repository;

import com.topjava.graduation.restaurant.entity.Dish;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.topjava.graduation.restaurant.entity.Dish.GRAPH_DISH_WITH_RESTAURANT;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @EntityGraph(GRAPH_DISH_WITH_RESTAURANT)
    List<Dish> getAllByRestaurantId(int restaurantId);

    @EntityGraph(GRAPH_DISH_WITH_RESTAURANT)
    List<Dish> getAllByDateAdded(LocalDate localDate);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=?1")
    Integer deleteDishById(int dishId);

    @Override
    @EntityGraph(GRAPH_DISH_WITH_RESTAURANT)
    List<Dish> findAll();

    @Override
    @EntityGraph(GRAPH_DISH_WITH_RESTAURANT)
    Optional<Dish> findById(Integer integer);

    @EntityGraph(GRAPH_DISH_WITH_RESTAURANT)
    @Query("SELECT d FROM Dish d " +
            "WHERE d.name=?1 AND d.restaurant.id=?2 AND d.dateAdded=?3")
    Optional<Dish> findByNameAndRestaurantIdAndDateAdded(String name, int restaurantId, LocalDate dateAdded);

}
