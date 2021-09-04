package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.dto.RestaurantResponseDTO;
import com.topjava.graduation.restaurant.entity.Restaurant;
import com.topjava.graduation.restaurant.exception.EntityAlreadyExistException;
import com.topjava.graduation.restaurant.exception.EntityNotFoundException;
import com.topjava.graduation.restaurant.repository.RestaurantRepository;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    RestaurantRepository restaurantRepository;

    @InjectMocks
    RestaurantService restaurantService;

    @Captor
    ArgumentCaptor<Restaurant> restaurantCaptor;

    @Test
    void create_restaurantAbsent_success() {

        Mockito.when(restaurantRepository.save(restaurantCaptor.capture())).thenReturn(getRestaurantAdriano());

        var resultRestaurant = restaurantService.create(getRestaurantCreationAdriano());

        var expectedSavedRestaurant = new Restaurant(0, "Adriano's pizza", "72 Mill Lane",
                null, null);
        assertTrue(EqualsBuilder.reflectionEquals(getRestaurantResponseAdriano(), resultRestaurant));
        assertTrue(EqualsBuilder.reflectionEquals(expectedSavedRestaurant, restaurantCaptor.getValue()));
    }

    @Test
    void create_restaurantExist_throwException() {
        Mockito.when(restaurantRepository.findByNameAndAddress(anyString(), anyString())).thenReturn(Optional.of(getRestaurantDominos()));

        var restaurantCreationDominos = getRestaurantCreationDominos();
        assertThrows(EntityAlreadyExistException.class, () -> restaurantService.create(restaurantCreationDominos));
    }

    @Test
    void update_restaurantExist_success() {
        Mockito.when(restaurantRepository.findById(2)).thenReturn((Optional.of(getRestaurantBangalore())));

        var actualResponseRestaurant = restaurantService.update(2, getRestaurantCreationAdriano());

        var expectedResponseRestaurant = new RestaurantResponseDTO(2, "Adriano's pizza",
                "72 Mill Lane", 1);
        assertEquals(expectedResponseRestaurant, actualResponseRestaurant);
        var expectedSavedRestaurant = new Restaurant(2, "Adriano's pizza", "72 Mill Lane",
                null, null);
        Mockito.verify(restaurantRepository, times(1)).save(restaurantCaptor.capture());
        assertTrue(EqualsBuilder.reflectionEquals(expectedSavedRestaurant, restaurantCaptor.getValue(),
                "votes", "dishes"));
    }
    //   @CachePut(value = "restaurantDTOs", key = "#restaurantId")
    //    public RestaurantResponseDTO update(int restaurantId, RestaurantCreationDTO restaurantCreationDTO) {
    //        var oldRestaurant = restaurantRepository.findById(restaurantId)
    //                .orElseThrow(() -> new EntityNotFoundException(
    //                        restaurantErrorMessage(restaurantId)));
    //
    //        oldRestaurant.setName(restaurantCreationDTO.getName());
    //        oldRestaurant.setAddress(restaurantCreationDTO.getAddress());
    //        restaurantRepository.save(oldRestaurant);
    //
    //        return toRestaurantDto(oldRestaurant);
    //    }

    @Test
    void getById_restaurantExist_success() {
        Mockito.when(restaurantRepository.findById(15)).thenReturn((Optional.of(getRestaurantDominos())));

        RestaurantResponseDTO resultResponse = restaurantService.getById(15);
        var expectedRestaurantResponse = getRestaurantResponseDominos();
        assertEquals(expectedRestaurantResponse, resultResponse);
    }

    @Test
    void getById_restaurantAbsent_throwException() {
        Mockito.when(restaurantRepository.findById(15)).thenReturn((Optional.empty()));
        assertThrows(EntityNotFoundException.class, () -> restaurantService.getById(15));
    }

    @Test
    void getAll_returnList() {
        Mockito.when(restaurantRepository.findAll()).thenReturn(getRestaurants());

        List<RestaurantResponseDTO> actualResponseList = restaurantService.getAll();
        var expectedResponseList = getResponseRestaurants();
        assertEquals(expectedResponseList, actualResponseList);
    }

    @Test
    void deleteById_validId_success() {
        Mockito.when(restaurantRepository.deleteRestaurantById(3)).thenReturn(1);
        restaurantService.deleteById(3);
        Mockito.verify(restaurantRepository, times(1)).deleteRestaurantById(3);
    }

    @Test
    void deleteById_restaurantAbsent_throwException() {
        Mockito.when(restaurantRepository.deleteRestaurantById(3)).thenReturn(0);
        assertThrows(EntityNotFoundException.class, () -> restaurantService.deleteById(3));
    }
}





