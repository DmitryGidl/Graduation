package com.topjava.graduation.restaurant.repository;

import com.topjava.graduation.restaurant.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    Vote getByUserIdAndVoteDate (int userId, LocalDate localDate);
    Optional<Vote> findByUserIdAndId(int userId, int voteId);
    Optional<Vote> findByUserIdAndVoteDate (int userId, LocalDate localDate);
    List<Vote> getByVoteDate(LocalDate localDate);
    List<Vote> getByUserId(int userId);
    Integer deleteVoteById(int voteId);
    Integer deleteVoteByUserId(int userId);
}
