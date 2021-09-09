package com.topjava.graduation.restaurant.repository;

import com.topjava.graduation.restaurant.entity.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.topjava.graduation.restaurant.entity.Vote.GRAPH_VOTE_WITH_RESTAURANT;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @EntityGraph(GRAPH_VOTE_WITH_RESTAURANT)
    @Query("SELECT v FROM Vote  v LEFT JOIN FETCH v.user WHERE v.user.id=?1 AND v.voteDate=?2")
    Vote getByUserIdAndVoteDate(int userId, LocalDate localDate);

    @EntityGraph(GRAPH_VOTE_WITH_RESTAURANT)
    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.user WHERE v.user.id=?1 AND v.id=?2")
    Optional<Vote> findByUserIdAndId(int userId, int voteId);

    @EntityGraph(GRAPH_VOTE_WITH_RESTAURANT)
    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.user WHERE v.user.id=?1 AND v.voteDate=?2")
    Optional<Vote> findByUserIdAndVoteDate(int userId, LocalDate localDate);

    @EntityGraph(GRAPH_VOTE_WITH_RESTAURANT)
    @Query("SELECT v FROM Vote  v LEFT JOIN FETCH v.user")
    @Override
    List<Vote> findAll();

    @EntityGraph(GRAPH_VOTE_WITH_RESTAURANT)
    @Query("SELECT v FROM Vote  v LEFT JOIN FETCH v.user")
    List<Vote> getByVoteDate(LocalDate localDate);

    @EntityGraph(GRAPH_VOTE_WITH_RESTAURANT)
    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.user WHERE v.user.id=?1")
    List<Vote> getByUserId(int userId);

    @Override
    @EntityGraph(GRAPH_VOTE_WITH_RESTAURANT)
    @Query("SELECT v FROM Vote v LEFT JOIN FETCH v.user WHERE v.id=?1")
    Optional<Vote> findById(Integer integer);

    @Modifying
    @Transactional
    @Query("DELETE FROM Vote v WHERE v.id=?1")
    Integer deleteVoteById(int voteId);

    @Modifying
    @Transactional
    @Query("DELETE FROM  Vote v WHERE v.user.id =?1 AND v.voteDate=?2")
    Integer deleteVoteByUserIdAndVoteDate(int userId, LocalDate voteDate);
}


