package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.dto.UserDTO;
import com.topjava.graduation.restaurant.entity.Role;
import com.topjava.graduation.restaurant.entity.User;
import com.topjava.graduation.restaurant.exception.UserAlreadyExistException;
import com.topjava.graduation.restaurant.repository.UserRepository;
import com.topjava.graduation.restaurant.security.AuthenticatedUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)

public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Captor
    ArgumentCaptor<User> userCaptor;

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

    @Test
    void registerNewUserAccount_userAbsent_sucess() {
        String email = "mytestemail@mail.com";
        var userDto = new UserDTO("testUsername", email, "somepassword");
        var user = new User(0, "testUsername", email, "somepassword",
                true, Set.of(Role.USER), null);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(null);

        userService.registerNewUserAccount(userDto);

        Mockito.verify(userRepository, times(1)).save(userCaptor.capture());
        assertTrue(reflectionEquals(user, userCaptor.getValue(), "password"));

    }

    @Test
    void registerNewUserAccount_userExist_throwException() {
        String email = "mytestemail@mail.com";
        var userDto = new UserDTO("testUsername", email, "somepassword");
        var user = new User(0, "testUsername", email, "somepassword",
                true, Set.of(Role.USER), null);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(user);

        assertThrows(UserAlreadyExistException.class, () -> userService.registerNewUserAccount(userDto));
    }
}
