package com.fiipractic.twitter.service;

import com.fiipractic.twitter.model.LogInForm;
import com.fiipractic.twitter.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    void registerUser(User user);

    User getUserById(String id);

    User getUserByEmail(String email);

    void updateUser(String id, User user);

    void patchUser(String id, Map<String, String> user);

    void deleteUser(String id);

    void authenticateUser(LogInForm logInForm);


}
