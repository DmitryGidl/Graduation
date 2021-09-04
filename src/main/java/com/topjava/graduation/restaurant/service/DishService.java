package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.dto.DishCreationDTO;
import com.topjava.graduation.restaurant.dto.DishResponseDTO;
import com.topjava.graduation.restaurant.entity.Dish;
import com.topjava.graduation.restaurant.exception.EntityAlreadyExistException;
import com.topjava.graduation.restaurant.exception.EntityNotFoundException;
import com.topjava.graduation.restaurant.mapper.DishMapper;
import com.topjava.graduation.restaurant.repository.DishRepository;
import com.topjava.graduation.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.topjava.graduation.restaurant.mapper.DishMapper.toDishResponseDTO;
import static com.topjava.graduation.restaurant.service.RestaurantService.restaurantErrorMessage;

@Service
public class DishService {
    DishRepository dishRepository;
    RestaurantRepository restaurantRepository;
    Clock clock;

    @Autowired
    public DishService(DishRepository dishRepository, RestaurantRepository restaurantRepository, Clock clock) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.clock = clock;
    }

    @Cacheable(value = "dishDTOList", key = "-1")
    public List<DishResponseDTO> getAll() {
        List<Dish> dishlsit = dishRepository.findAll();
        return dishlsit.stream()
                .map(DishMapper::toDishResponseDTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "dishDTOList", key = "-2")
    public List<DishResponseDTO> getAllToday() {
        List<Dish> dishlsit = dishRepository.getAllByDateAdded(LocalDate.now(clock));
        return dishlsit.stream()
                .map(DishMapper::toDishResponseDTO)
                .collect(Collectors.toList());
    }

    @CachePut(value = "dishDTOs", key = "#dishId")
    public DishResponseDTO update(int dishId, DishCreationDTO dishCreationDTO) {
        var oldDish = dishRepository.findById(dishId).orElseThrow(() -> new EntityNotFoundException(
                dishtErrorMessage(dishId)));

        oldDish.setName(dishCreationDTO.getName());
        oldDish.setPrice(dishCreationDTO.getPrice());
        oldDish.setDateAdded(dishCreationDTO.getDateAdded());

        int newRestaurantId = dishCreationDTO.getRestaurantId();
        var newRestaurant = restaurantRepository.findById(newRestaurantId)
                .orElseThrow(() -> new EntityNotFoundException(restaurantErrorMessage(newRestaurantId)));
        oldDish.setRestaurant(newRestaurant);

        dishRepository.save(oldDish);
        return toDishResponseDTO(oldDish);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "dishDTOList", allEntries = true),
                    @CacheEvict(cacheNames = "dishDTOs", key = "#dishId")
            })
    public void delete(int dishId) {
        int deleted = dishRepository.deleteDishById(dishId);
        if (deleted == 0) {
            throw new EntityNotFoundException(
                    dishtErrorMessage(dishId));
        }
    }


    @CachePut(value = "dishDTOs", key = "#result.id")
    @CacheEvict(cacheNames = "dishDTOList", allEntries = true)
    public DishResponseDTO createDish(DishCreationDTO dishCreationDTO) {
        int restaurantId = dishCreationDTO.getRestaurantId();

        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(restaurantErrorMessage(restaurantId)));

        var sameDish = dishRepository
                .findByNameAndRestaurantIdAndDateAdded(
                        dishCreationDTO.getName(), restaurantId, dishCreationDTO.getDateAdded());

        if (sameDish.isPresent()) {
            throw new EntityAlreadyExistException(
                    "Dish with the same Name and Date is already present in the restaurant menu");
        }

        var dish = DishMapper.toDish(dishCreationDTO, restaurant);
        return toDishResponseDTO(dishRepository.save(dish));
    }

    @Cacheable(value = "dishDTOs", key = "#dishId")
    public DishResponseDTO getOne(int dishId) {
        var dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new EntityNotFoundException(dishtErrorMessage(dishId)));
        return DishMapper.toDishResponseDTO(dish);
    }

    @Cacheable(value = "dishDTOList", key = "#restaurantId")
    public List<DishResponseDTO> getDishHistoryByRestaurant(int restaurantId) {
        return dishRepository.getAllByRestaurantId(restaurantId)
                .stream()
                .map(DishMapper::toDishResponseDTO)
                .collect(Collectors.toList());
    }

    public static String dishtErrorMessage(int dishId) {
        return "Dish with id " + dishId + " does not exist in DB";
    }


}

