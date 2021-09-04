package com.topjava.graduation.restaurant.controller.restaurant;

import com.topjava.graduation.restaurant.dto.RestaurantCreationDTO;
import com.topjava.graduation.restaurant.dto.RestaurantResponseDTO;
import com.topjava.graduation.restaurant.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.topjava.graduation.restaurant.util.ExceptionUtil.throwExceptionIfBindingResultHasErrors;

@RestController
@RequestMapping("/admin/restaurant")

public class AdminRestaurantController {
    private RestaurantService restaurantService;

    public AdminRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponseDTO create(@RequestBody @Valid RestaurantCreationDTO restaurantCreationDTO,
                                        BindingResult bindingResult) {
        System.out.println(bindingResult.hasErrors());
        throwExceptionIfBindingResultHasErrors(bindingResult);
        return restaurantService.create(restaurantCreationDTO);
    }

    @PutMapping("/{id}")
    public RestaurantResponseDTO update(@PathVariable int id, @RequestBody
    @Valid RestaurantCreationDTO restaurantCreationDTO, BindingResult bindingResult) {
        throwExceptionIfBindingResultHasErrors(bindingResult);
        return restaurantService.update(id, restaurantCreationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        restaurantService.deleteById(id);
    }


}
