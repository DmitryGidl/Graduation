package com.topjava.graduation.restaurant.service;

import com.topjava.graduation.restaurant.dto.UserDTO;
import com.topjava.graduation.restaurant.entity.User;
import com.topjava.graduation.restaurant.exception.UserAlreadyExistException;
import com.topjava.graduation.restaurant.repository.UserRepository;
import com.topjava.graduation.restaurant.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User email username " + email + " does not exist");
        }
        return new AuthenticatedUser(user);
    }

    public void registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistException {
        String email = userDto.getEmail();
        if (ifUserExists(email)) {
            throw new UserAlreadyExistException("Email address: "
                    + email + " is already registered");
        }

        var user = toUser(userDto);
        String userPassword = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(userPassword));
        userRepository.save(user);
    }

    public User toUser(UserDTO userDto) {
        return new User(userDto.getUsername(), userDto.getEmail(),
                userDto.getPassword());
    }

    private boolean ifUserExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }
}
