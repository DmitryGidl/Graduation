package com.topjava.graduation.restaurant.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dish extends AbstractNamedEntity {
    @NotNull
    @Digits(integer = 15, fraction = 2)
    private double price;
    @NotNull
    private LocalDate dateAdded;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Restaurant restaurant;

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Dish(String name, double price, LocalDate dateAdded, Restaurant restaurant) {
        super(name);
        this.price = price;
        this.dateAdded = dateAdded;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, double price, LocalDate dateAdded, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.dateAdded = dateAdded;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return super.toString() +
                " Dish{" +
                "price=" + price +
                ", dateAdded=" + dateAdded +
                ", restaurant=" + restaurant +
                '}';
    }
}
