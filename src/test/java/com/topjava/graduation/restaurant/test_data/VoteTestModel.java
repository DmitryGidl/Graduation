package com.topjava.graduation.restaurant.test_data;

import com.topjava.graduation.restaurant.dto.VoteCreationDTO;
import com.topjava.graduation.restaurant.dto.VoteResponseDTO;
import com.topjava.graduation.restaurant.entity.Vote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.topjava.graduation.restaurant.test_data.RestaurantTestModel.*;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.getBasicUser;

public abstract class VoteTestModel extends TestBaseData {

    // Votes


    // VoteCreationDTOs
    public static VoteCreationDTO getVoteCreationDominos() {
        return new VoteCreationDTO(1);
    }

    public static VoteCreationDTO getVoteCreationMamamia() {
        return new VoteCreationDTO(2);
    }

    public static VoteCreationDTO getVoteCreationAdriano() {
        return new VoteCreationDTO(3);
    }

    // VoteResponseDTOs
    public static VoteResponseDTO getVoteResponseDominos() {
        return new VoteResponseDTO(1, "User", "Dominos Pizza",
                "Басейна, 17", 1, todayDate);
    }

    public static VoteResponseDTO getVoteResponseMamamia() {
        return new VoteResponseDTO(2, "User", "Mamamia",
                "проспект Победы, 9Б", 2, todayDate);
    }

    public static VoteResponseDTO getVoteResponseAdriano() {
        return new VoteResponseDTO(3, "User", "Adriano's pizza",
                "Глибочицкая, 33/37", 3, todayDate);
    }

    public static List<VoteResponseDTO> getResponseVotes() {
        return new ArrayList<>(Arrays.asList(getVoteResponseDominos(),
                getVoteResponseMamamia(), getVoteResponseAdriano()));
    }

    // Votes
    public static Vote getVoteDominos() {
        return new Vote(1, getRestaurantDominos(), todayDate, getBasicUser());
    }

    public static Vote getVoteMamamia() {
        return new Vote(2, getRestaurantMamamia(), todayDate, getBasicUser());
    }

    public static Vote getVoteAdriano() {
        return new Vote(3, getRestaurantAdriano(), todayDate, getBasicUser());
    }

    public static List<Vote> getVotes() {
        return new ArrayList<>(Arrays.asList(getVoteDominos(), getVoteMamamia(), getVoteAdriano()));
    }

}
