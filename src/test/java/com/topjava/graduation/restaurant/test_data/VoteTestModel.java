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

    public static VoteCreationDTO getVoteCreationBangalore() {
        return new VoteCreationDTO(2);
    }

    public static VoteCreationDTO getVoteCreationAdriano() {
        return new VoteCreationDTO(3);
    }

    // VoteResponseDTOs
    public static VoteResponseDTO getVoteResponseDominos() {
        return new VoteResponseDTO(1, "User", "Dominos Pizza",
                "30 Queen Street", 1, todayDate);
    }

    public static VoteResponseDTO getVoteResponseBangalore() {
        return new VoteResponseDTO(2, "User", "Bangalore Spices",
                "87 Stanley Road", 2, todayDate);
    }

    public static VoteResponseDTO getVoteResponseAdriano() {
        return new VoteResponseDTO(3, "User", "Adriano's pizza",
                "72 Mill Lane", 3, todayDate);
    }

    public static List<VoteResponseDTO> getResponseVotes() {
        return new ArrayList<>(Arrays.asList(getVoteResponseDominos(),
                getVoteResponseBangalore(), getVoteResponseAdriano()));
    }

    // Votes
    public static Vote getVoteDominos() {
        return new Vote(1, getRestaurantDominos(), todayDate, getBasicUser());
    }

    public static Vote getVoteBangalore() {
        return new Vote(2, getRestaurantBangalore(), todayDate, getBasicUser());
    }

    public static Vote getVoteAdriano() {
        return new Vote(3, getRestaurantAdriano(), todayDate, getBasicUser());
    }

    public static List<Vote> getVotes() {
        return new ArrayList<>(Arrays.asList(getVoteDominos(), getVoteBangalore(), getVoteAdriano()));
    }

}
