package com.topjava.graduation.restaurant.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RestaurantResponseDTO {

    private int id;
    private String name;
    private String address;
    private long votesToday;
}
