package com.topjava.graduation.restaurant.controller.registration;

import com.topjava.graduation.restaurant.dto.UserDTO;
import com.topjava.graduation.restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.topjava.graduation.restaurant.util.ExceptionUtil.throwExceptionIfBindingResultHasErrors;

@RestController
public class RegistrationController {
    UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        throwExceptionIfBindingResultHasErrors(bindingResult);
        userService.registerNewUserAccount(userDTO);

    }
}
