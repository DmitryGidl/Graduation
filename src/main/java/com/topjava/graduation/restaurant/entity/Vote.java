package com.topjava.graduation.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

import static com.topjava.graduation.restaurant.entity.Vote.GRAPH_VOTE_WITH_RESTAURANT;

@Entity
@Setter
@Getter
@NoArgsConstructor
@NamedEntityGraph(name = GRAPH_VOTE_WITH_RESTAURANT, attributeNodes =
@NamedAttributeNode("restaurant"))
public class Vote extends AbstractBaseEntity {
    public static final String GRAPH_VOTE_WITH_RESTAURANT = "Vote with restaurant.Graph";

    @ManyToOne
    @JsonBackReference
    private Restaurant restaurant;
    private LocalDate voteDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    public Vote(User user, Restaurant restaurant) {
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = LocalDate.now();
    }

    public Vote(Integer id, Restaurant restaurant, LocalDate voteDate, User user) {
        super(id);
        this.restaurant = restaurant;
        this.voteDate = voteDate;
        this.user = user;
    }

    @Override
    public String toString() {
        return super.toString() +
                " Vote{" +
                "restaurant=" + restaurant +
                ", voteDate=" + voteDate +
                ", user=" + user +
                '}';
    }
}
