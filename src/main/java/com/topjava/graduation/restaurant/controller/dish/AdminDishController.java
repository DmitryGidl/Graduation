package com.topjava.graduation.restaurant.controller.dish;

import com.topjava.graduation.restaurant.dto.DishCreationDTO;
import com.topjava.graduation.restaurant.dto.DishResponseDTO;
import com.topjava.graduation.restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.topjava.graduation.restaurant.util.ExceptionUtil.throwExceptionIfBindingResultHasErrors;

@RestController
@RequestMapping("/admin/dishes")
public class AdminDishController {

    private final DishService dishService;

    @Autowired
    public AdminDishController(DishService dishService) {
        this.dishService = dishService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public DishResponseDTO createDish(@RequestBody @Valid DishCreationDTO dishCreationDTO,
                                      BindingResult bindingResult) {
        throwExceptionIfBindingResultHasErrors(bindingResult);
        return dishService.createDish(dishCreationDTO);
    }

    @PutMapping("/{id}")
    public DishResponseDTO update(@PathVariable("id") int id,
                                  @RequestBody @Valid DishCreationDTO dishCreationDTO,
                                  BindingResult bindingResult) {
        throwExceptionIfBindingResultHasErrors(bindingResult);
        return dishService.update(id, dishCreationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        dishService.delete(id);
    }

}
