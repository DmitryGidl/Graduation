package com.topjava.graduation.restaurant;

import com.topjava.graduation.restaurant.entity.User;
import com.topjava.graduation.restaurant.security.AuthenticatedUser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.topjava.graduation.restaurant.test_data.UserTestModels.getAdminUser;
import static com.topjava.graduation.restaurant.test_data.UserTestModels.getBasicUser;

@TestConfiguration
public class ControllerTestConfig {

    @Bean
    @Primary
    public UserDetailsService userService() {
        var basicUser = getBasicUser();
        var adminUser = getAdminUser();
        return s -> {
            List<User> list = new ArrayList<>(Arrays.asList(basicUser, adminUser));
            return new AuthenticatedUser(
                    list.stream()
                            .filter(u -> u.getUsername().equals(s)).findAny()
                            .orElse(basicUser));
        };
    }
}



