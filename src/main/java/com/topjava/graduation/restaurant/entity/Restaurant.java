package com.topjava.graduation.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Restaurant extends AbstractNamedEntity {

    @NotBlank
    @Length(min = 3, max = 100)
    private String address;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Dish> dishes;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Vote> votes;

    public Restaurant(Integer id, String name, String address, List<Dish> dishes, List<Vote> votes) {
        super(id, name);
        this.address = address;
        this.dishes = dishes;
        this.votes = votes;
    }


    public Restaurant(String name, String address) {
        super(name);
        this.address = address;
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
        dish.setRestaurant(this);
    }

    public void removeDish(Dish dish) {
        this.dishes.remove(dish);
        dish.setRestaurant(null);
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
        vote.setRestaurant(this);
    }

    public void removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setRestaurant(null);
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return super.toString() +
                " Restaurant{" +
                "address='" + address + '\'' +
                '}' + super.toString();
    }
}