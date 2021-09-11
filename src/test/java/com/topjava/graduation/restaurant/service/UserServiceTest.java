package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.entity.User;
import com.topjava.graduation.restaurant.repository.UserRepository;
import com.topjava.graduation.restaurant.security.AuthenticatedUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void loadUserByUsername_userPresent_returnUserDetails() {
        String email = "mytestemail@mail.com";
        var user = new User(32, "testUsername", email, "somepassword",
                true, new HashSet<>(), new ArrayList<>());
        Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

        var actualAuthenticated = userService.loadUserByUsername(email);

        var expectedAuthenticated = new AuthenticatedUser(user);
        assertEquals(expectedAuthenticated, actualAuthenticated);
    }

    @Test
    void loadUserByUsername_userAbsent_throwException() {
        String email = "mytestemail@mail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));

    }


}
