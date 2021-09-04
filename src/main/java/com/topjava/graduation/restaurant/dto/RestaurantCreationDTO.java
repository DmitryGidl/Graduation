package com.topjava.graduation.restaurant.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RestaurantCreationDTO {

    @NotBlank
    @Length(min = 3, max = 100)
    private String name;
    @NotBlank
    @Length(min = 3, max = 100)
    private String address;

}


