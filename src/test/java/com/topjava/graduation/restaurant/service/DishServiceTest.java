package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.dto.DishCreationDTO;
import com.topjava.graduation.restaurant.dto.DishResponseDTO;
import com.topjava.graduation.restaurant.entity.Dish;
import com.topjava.graduation.restaurant.entity.Restaurant;
import com.topjava.graduation.restaurant.exception.EntityAlreadyExistException;
import com.topjava.graduation.restaurant.exception.EntityNotFoundException;
import com.topjava.graduation.restaurant.repository.DishRepository;
import com.topjava.graduation.restaurant.repository.RestaurantRepository;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.topjava.graduation.restaurant.test_data.DishTestModel.*;
import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.getRestaurantDominos;
import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.getRestaurantMamamia;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {
    @Mock
    DishRepository dishRepository;
    @Mock
    RestaurantRepository restaurantRepository;

    @Spy
    Clock clock = Clock.systemDefaultZone();

    @InjectMocks
    DishService dishService;

    @Captor
    ArgumentCaptor<Dish> dishCaptor;

    @Test
    void getAll_returnList() {
        Mockito.when(dishRepository.findAll()).thenReturn(getDishes());

        List<DishResponseDTO> actualDishResponse = dishService.getAll();

        var expectedDishResponse = getResponseDishes();
        assertEquals(expectedDishResponse, actualDishResponse);
    }

    @Test
    void getAllToday_returnList() {
        Mockito.when(dishRepository.getAllByDateAdded(LocalDate.now(clock))).thenReturn(getDishes());

        List<DishResponseDTO> actualDishResponse = dishService.getAllToday();

        var expectedDishResponse = getResponseDishes();
        assertEquals(expectedDishResponse, actualDishResponse);
    }

    @Test
    void getOne_dishExist_success() {
        Mockito.when(dishRepository.findById(3)).thenReturn(Optional.of(getDishMargarita()));
        var actualDishDto = dishService.getOne(3);

        var expectedDishDto = getDishResponseMargarita();
        assertEquals(expectedDishDto, actualDishDto);
    }

    @Test
    void update_dishExist_sucess() {
        var dishVegiBox = getDishVegibox();
        var restaurantMamamia = getRestaurantMamamia();
        Mockito.when(dishRepository.findById(12)).thenReturn(Optional.of(dishVegiBox));
        Mockito.when(restaurantRepository.findById(2)).thenReturn(Optional.of(restaurantMamamia));
        Mockito.when(dishRepository.save(dishCaptor.capture())).thenReturn(dishVegiBox);


        var actualResponseDish = dishService.update(12, getDishCreationDeepfried());

        var expectedSavedDish = new Dish(4, "Ассорти Deep Fried", 289, todayDate, restaurantMamamia);
        var expectedResponseDish = new DishResponseDTO(4, "Ассорти Deep Fried", 289,
                "Mamamia", "проспект Победы, 9Б", todayDate, 2);
        assertEquals(expectedResponseDish, actualResponseDish);
        assertTrue(EqualsBuilder.reflectionEquals(expectedSavedDish, dishCaptor.getValue()));
    }

    @Test
    void findById_dishAbsent_throwException() {
        var dishCreationDeepfried = getDishCreationDeepfried();
        Mockito.when(dishRepository.findById(12)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> dishService.update(12, dishCreationDeepfried));
    }

    @Test
    void deleteDishById_dishExist_success() {
        Mockito.when(dishRepository.deleteDishById(24)).thenReturn(1);
        dishService.delete(24);
        Mockito.verify(dishRepository, Mockito.times(1))
                .deleteDishById(24);
    }

    @Test
    void deleteDishById_dishAbsent_throwException() {
        Mockito.when(dishRepository.deleteDishById(24)).thenReturn(0);
        assertThrows(EntityNotFoundException.class, () -> dishService.delete(24));
    }

    @Test
    void createDish_dishAbsent_success() {
        Mockito.when(restaurantRepository.findById(1)).thenReturn(Optional.of(getRestaurantDominos()));
        Mockito.when(dishRepository.save(dishCaptor.capture())).thenReturn(getDishPeperoni());

        DishResponseDTO actualResponse = dishService.createDish(getDishCreationPepperoni());

        var expectedResponse = getDishResponsePepperoni();
        assertTrue(EqualsBuilder.reflectionEquals(expectedResponse, actualResponse));
        var expectedDishSaved = new Dish(0, "Пицца пеперони", 150,
                todayDate, new Restaurant(1, "Dominos Pizza",
                "Басейна, 17", null, null));
        assertTrue(EqualsBuilder.reflectionEquals(expectedDishSaved, dishCaptor.getValue()));
    }

    @Test
    void createDish_alreadyExist_throwException() {
        Mockito.when(restaurantRepository.findById(1)).thenReturn(Optional.of(getRestaurantDominos()));
        Mockito.when(dishRepository.findByNameAndRestaurantIdAndDateAdded(anyString(), anyInt(), any(LocalDate.class)))
                .thenReturn(Optional.of(getDishPeperoni()));

        var dishCreationPepperoni = getDishCreationPepperoni();
        assertThrows(EntityAlreadyExistException.class, () -> dishService.createDish(dishCreationPepperoni));
    }

    @Test
    void createDish_invalidRestaurantId_throwException() {
        var dishCreationInvalid = new DishCreationDTO(
                "FailedRestaurantDish", 323.32, 253, LocalDate.now(clock));
        Mockito.when(restaurantRepository.findById(253)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> dishService.createDish(dishCreationInvalid));
    }

    @Test
    void getDishHistoryByRestaurant_validRestaurant_returnDishList() {
        Mockito.when(dishRepository.getAllByRestaurantId(1)).thenReturn(getDishes());

        List<DishResponseDTO> actualResponseList = dishService.getDishHistoryByRestaurant(1);

        var expectedResponseList = getResponseDishes();
        assertEquals(expectedResponseList, actualResponseList);
    }


}
