package com.fiipractic.twitter.controller;

import com.fiipractic.twitter.model.LogInForm;
import com.fiipractic.twitter.model.User;
import com.fiipractic.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/createAccountForm", produces = MediaType.APPLICATION_JSON_VALUE)
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }

    @PostMapping(value = "/logIn", produces = MediaType.APPLICATION_JSON_VALUE)
    public void loginUser(@RequestBody LogInForm logInForm) {
        userService.authenticateUser(logInForm);

    }


}
