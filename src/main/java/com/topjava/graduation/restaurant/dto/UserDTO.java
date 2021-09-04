package com.topjava.graduation.restaurant.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {

    @NotBlank
    @Length(min = 4, max = 25)
    private String username;
    @NotEmpty
    @Email
    private String email;
    @NotBlank
    @Length(min = 8, max = 60)
    private String password;

}
