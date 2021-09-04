package com.topjava.graduation.restaurant.dto;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DishResponseDTO {

    private int id;
    private String name;
    private double price;
    private String restaurantName;
    private String restaurantAddress;
    private LocalDate dateAdded;
    private int restaurantId;
}
