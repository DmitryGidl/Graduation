package com.topjava.graduation.restaurant.test_data;

import com.topjava.graduation.restaurant.entity.Role;
import com.topjava.graduation.restaurant.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public abstract class UserTestModels {
    // DummyUsers
    public static final String USER_USERNAME = "BobUser";
    public static final String ADMIN_USERNAME = "JohnAdmin";

    public static User getBasicUser() {
        return new User(122, USER_USERNAME, "user@gmail.com", "password",
                true, new HashSet<>(Collections.singletonList(Role.USER)), new ArrayList<>());
    }

    public static User getAdminUser() {
        return new User(144, ADMIN_USERNAME, "admin@gmail.com", "password",
                true, new HashSet<>(Collections.singletonList(Role.ADMIN)), new ArrayList<>());
    }

}
