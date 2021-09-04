package com.topjava.graduation.restaurant.mapper;

import com.topjava.graduation.restaurant.dto.VoteResponseDTO;
import com.topjava.graduation.restaurant.entity.Vote;

public abstract class VoteMapper {

    private VoteMapper() {
    }

    public static VoteResponseDTO toVoteResponseDTO(Vote vote) {
        var restaurant = vote.getRestaurant();
        var user = vote.getUser();

        return new VoteResponseDTO(vote.getId(), user.getUsername(),
                restaurant.getName(), restaurant.getAddress(), restaurant.getId(), vote.getVoteDate());
    }
}
