package com.fiipractic.twitter.service;

import com.fiipractic.twitter.exception.UserNotFoundException;
import com.fiipractic.twitter.model.LogInForm;
import com.fiipractic.twitter.model.User;
import com.fiipractic.twitter.repository.UserDAO;
import com.fiipractic.twitter.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userRepository;

    @Autowired
    public UserServiceImpl(UserDAO userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        userRepository.createUser(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword());
    }

    @Override
    public User getUserById(String id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public void authenticateUser(LogInForm logInForm) {
        User user;

        if (logInForm.getAuthType().equals("email"))
            user = getUserByEmail(logInForm.getCredential());
        else
            user = getUserById(logInForm.getCredential());


        if (!user.getPassword().equals(logInForm.getPassword()))
            throw new UserNotFoundException("Parola este gresita");


    }

    @Override
    public void followUser(String credential, String followCredential) {
        userRepository.followUser(credential, followCredential);
    }

    @Override
    public void unfollowUser(String credential, String followCredential) {
        userRepository.unfollowUser(credential, followCredential);
    }

    @Override
    public boolean isFollowing(String credential, String followCredential) {
        return userRepository.isFollowing(credential, followCredential);
    }

    @Override
    public int getFollowingNumber(String credential) {
        return userRepository.getFollowingNumber(credential);
    }

    @Override
    public int getFollowerNumber(String credential) {
        return userRepository.getFollowerNumber(credential);
    }

    @Override
    public List<String> getUsersListFollowedBy(String credential) throws Exception {
        return userRepository.getUsersListFollowed(credential);
    }

    @Override
    public List<String> getUsersListFollowing(String credential) throws Exception {
        return userRepository.getUsersListFollowing(credential);
    }


}
