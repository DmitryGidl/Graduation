package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.dto.RestaurantCreationDTO;
import com.topjava.graduation.restaurant.dto.RestaurantResponseDTO;
import com.topjava.graduation.restaurant.entity.Restaurant;
import com.topjava.graduation.restaurant.exception.EntityAlreadyExistException;
import com.topjava.graduation.restaurant.exception.EntityNotFoundException;
import com.topjava.graduation.restaurant.mapper.RestaurantMapper;
import com.topjava.graduation.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.topjava.graduation.restaurant.mapper.RestaurantMapper.toRestaurant;
import static com.topjava.graduation.restaurant.mapper.RestaurantMapper.toRestaurantDto;

@Service
public class RestaurantService {

    RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @CachePut(value = "restaurantDTOs", key = "#result.id")
    public RestaurantResponseDTO create(RestaurantCreationDTO restaurantCreationDTO) {

        var sameRestaurant = restaurantRepository.findByNameAndAddress(
                restaurantCreationDTO.getName(), restaurantCreationDTO.getAddress());
        if (sameRestaurant.isPresent()) {
            throw new EntityAlreadyExistException(
                    "Restaurant with the same name and address is already present in DB");
        }

        var restaurant = toRestaurant(restaurantCreationDTO);
        return toRestaurantDto(restaurantRepository.save(restaurant));
    }

    @CachePut(value = "restaurantDTOs", key = "#restaurantId")
    public RestaurantResponseDTO update(int restaurantId, RestaurantCreationDTO restaurantCreationDTO) {
        var oldRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        restaurantErrorMessage(restaurantId)));

        oldRestaurant.setName(restaurantCreationDTO.getName());
        oldRestaurant.setAddress(restaurantCreationDTO.getAddress());
        restaurantRepository.save(oldRestaurant);

        return toRestaurantDto(oldRestaurant);
    }

    @Cacheable(value = "restaurantDTOs", key = "#restaurantId")
    public RestaurantResponseDTO getById(int restaurantId) {
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        restaurantErrorMessage(restaurantId)
                ));

        return toRestaurantDto(restaurant);
    }

    @Cacheable(value = "restaurantDTOList", key = "-1")
    public List<RestaurantResponseDTO> getAll() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        return restaurantList.stream()
                .map(RestaurantMapper::toRestaurantDto)
                .collect(Collectors.toList());
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "restaurantDTOList", allEntries = true),
                    @CacheEvict(value = "restaurantDTOs", key = "#restaurantId")
            })
    @Transactional
    public void deleteById(int restaurantId) {
        int deleted = restaurantRepository.deleteRestaurantById(restaurantId);
        if (deleted == 0) {
            throw new EntityNotFoundException(
                    restaurantErrorMessage(restaurantId));
        }
    }

    public static String restaurantErrorMessage(int restaurantId) {
        return "Restaurant with id " + restaurantId + " does not exist in DB";
    }
}
