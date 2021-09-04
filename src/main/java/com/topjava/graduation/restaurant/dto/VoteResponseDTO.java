package com.topjava.graduation.restaurant.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class VoteResponseDTO {

    private int id;
    private String username;
    private String restaurantName;
    private String restaurantAddress;
    private int restaurantId;
    private LocalDate voteDate;
}
