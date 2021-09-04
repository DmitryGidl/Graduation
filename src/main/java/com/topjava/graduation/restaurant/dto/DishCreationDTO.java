package com.topjava.graduation.restaurant.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DishCreationDTO {

    @NotBlank
    @Length(min = 3, max = 100)
    private String name;
    @NotNull
    @Digits(integer = 15, fraction = 2)
    @Positive
    private double price;
    @NotNull
    @Digits(integer = 14, fraction = 0)
    @Positive
    private int restaurantId;
    @NotNull
    LocalDate dateAdded;
}
