package com.topjava.graduation.restaurant.repository;

import com.topjava.graduation.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.topjava.graduation.restaurant.entity.Restaurant.GRAPH_RESTAURANT_WITH_VOTES;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=?1")
    int deleteRestaurantById(int id);

    @EntityGraph(GRAPH_RESTAURANT_WITH_VOTES)
    Optional<Restaurant> findByNameAndAddress(String name, String address);

    @EntityGraph(GRAPH_RESTAURANT_WITH_VOTES)
    List<Restaurant> findAll();

    @Override
    @EntityGraph(GRAPH_RESTAURANT_WITH_VOTES)
    Optional<Restaurant> findById(Integer integer);
}
