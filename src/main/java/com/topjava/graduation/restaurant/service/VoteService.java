package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.dto.VoteCreationDTO;
import com.topjava.graduation.restaurant.dto.VoteResponseDTO;
import com.topjava.graduation.restaurant.entity.User;
import com.topjava.graduation.restaurant.entity.Vote;
import com.topjava.graduation.restaurant.exception.EntityNotFoundException;
import com.topjava.graduation.restaurant.exception.LateToVoteException;
import com.topjava.graduation.restaurant.mapper.VoteMapper;
import com.topjava.graduation.restaurant.repository.RestaurantRepository;
import com.topjava.graduation.restaurant.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.topjava.graduation.restaurant.mapper.VoteMapper.toVoteResponseDTO;
import static com.topjava.graduation.restaurant.service.RestaurantService.restaurantErrorMessage;
import static com.topjava.graduation.restaurant.util.TimeUtil.getVoteFinishTime;

@Service
public class VoteService {

    VoteRepository voteRepository;
    RestaurantRepository restaurantRepository;
    Clock clock;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, Clock clock) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.clock = clock;
    }

    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "restaurantDTOList", allEntries = true),
                    @CacheEvict(cacheNames = "restaurantDTOs", key = "#voteCreationDTO.restaurantId")
            })
    public VoteResponseDTO addVote(User user, VoteCreationDTO voteCreationDTO) {
        ifLateToVote();
        int restaurantId = voteCreationDTO.getRestaurantId();
        var restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException(
                        restaurantErrorMessage(restaurantId)));

        return toVoteResponseDTO(voteRepository.findByUserIdAndVoteDate(user.getId(), LocalDate.now(clock))
                .map(oldVote -> updateLogic(oldVote, voteCreationDTO))
                .orElseGet(
                        () -> voteRepository.save(new Vote(user, restaurant))));
    }
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "restaurantDTOList", allEntries = true),
                    @CacheEvict(cacheNames = "restaurantDTOs", key = "#voteCreationDTO.restaurantId")
            })
    public VoteResponseDTO updateCurrentUserVote(User user, VoteCreationDTO voteCreationDTO) {
                ifLateToVote();
        int userId = user.getId();
        Vote oldVote = voteRepository.findByUserIdAndVoteDate(userId, LocalDate.now(clock))
                .orElseThrow(() -> new EntityNotFoundException(userId + " has not Voted"));
        return toVoteResponseDTO(updateLogic(oldVote, voteCreationDTO));
    }

    public Vote updateLogic(Vote oldVote, VoteCreationDTO voteCreationDTO) {
        int newRestaurantId = voteCreationDTO.getRestaurantId();
        var newRestaurant = restaurantRepository.findById(newRestaurantId)
                .orElseThrow(() -> new EntityNotFoundException(restaurantErrorMessage(newRestaurantId)));
        oldVote.setRestaurant(newRestaurant);
        return voteRepository.save(oldVote);
    }


    public List<VoteResponseDTO> getAllHistory() {
        List<Vote> voteList = voteRepository.findAll();
        return voteList.stream()
                .map(VoteMapper::toVoteResponseDTO)
                .collect(Collectors.toList());
    }


    public List<VoteResponseDTO> getAllToday() {
        List<Vote> voteList = voteRepository.getByVoteDate(LocalDate.now(clock));
        return voteList.stream()
                .map(VoteMapper::toVoteResponseDTO)
                .collect(Collectors.toList());
    }

    public List<VoteResponseDTO> getUserVoteHistory(int id) {
        List<Vote> voteList = voteRepository.getByUserId(id);
        return voteList.stream()
                .map(VoteMapper::toVoteResponseDTO)
                .collect(Collectors.toList());
    }

    public VoteResponseDTO getByid(int voteId) {
        var vote = voteRepository.findById(voteId).orElseThrow(() ->
                new EntityNotFoundException(voteErrorMessage(voteId)));
        return toVoteResponseDTO(vote);
    }

    @Transactional
    public void deleteById(int voteId) {
        int delete = voteRepository.deleteVoteById(voteId);
        if (delete == 0) {
            throw new EntityNotFoundException(
                    voteErrorMessage(voteId));
        }
    }

    @Transactional
    public void deleteCurrentUserVote(int userId) {
        int deleteInt = voteRepository.deleteVoteByUserId(userId);
        if (deleteInt == 0) {
            throw new EntityNotFoundException(userId + " has not voted");
        }
    }

    public VoteResponseDTO update(int voteId, VoteCreationDTO voteCreationDTO) {
        ifLateToVote();
        var oldVote = voteRepository.findById(voteId).orElseThrow(() ->
                new EntityNotFoundException(voteErrorMessage(voteId)));
        int newRestaurantId = voteCreationDTO.getRestaurantId();
        var newRestaurant = restaurantRepository.findById(newRestaurantId)
                .orElseThrow(() -> new EntityNotFoundException(restaurantErrorMessage(newRestaurantId)));
        oldVote.setRestaurant(newRestaurant);
        voteRepository.save(oldVote);
        return toVoteResponseDTO(oldVote);
    }

    public VoteResponseDTO getCurrentUserVote(int userId) {
        var vote = voteRepository.getByUserIdAndVoteDate(userId, LocalDate.now(clock));
        if (vote == null) return null;
        return toVoteResponseDTO(vote);
    }

    public VoteResponseDTO getOneVoteCurrentUser(int userId, int voteId) {
        var vote = voteRepository.findByUserIdAndId(userId, voteId)
                .orElseThrow(() -> new EntityNotFoundException(voteHistoryErrorMessage(voteId)));
        return toVoteResponseDTO(vote);
    }

    public void ifLateToVote() {
        var localTime = LocalTime.now(clock);
        if (!localTime.isBefore(getVoteFinishTime())) {
            throw new LateToVoteException("Vote can not be changed after 11 a.m. " +
                    "now is " + localTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        }
    }

    public static String voteErrorMessage(int voteId) {
        return "Vote with id " + voteId + " does not exist in DB";
    }

    public static String voteHistoryErrorMessage(int voteId) {
        return "Vote with id " + voteId + " does not exist in vote history of this User";
    }

}
