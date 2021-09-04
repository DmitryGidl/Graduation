package com.topjava.graduation.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AbstractNamedEntity extends AbstractBaseEntity {
    @NotBlank
    @Length(min = 3, max = 100)
    private String name;

    public AbstractNamedEntity(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {

        return super.toString() +
                " AbstractNamedEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
