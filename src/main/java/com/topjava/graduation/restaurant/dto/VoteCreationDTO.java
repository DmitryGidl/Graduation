package com.topjava.graduation.restaurant.dto;

import com.topjava.graduation.restaurant.validation.NoHtml;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class VoteCreationDTO {

    @NotNull
    @Digits(integer = 15, fraction = 0)
    @Positive
    private int restaurantId;
}
