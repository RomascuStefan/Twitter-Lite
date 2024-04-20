package com.fiipractic.twitter.service;

import com.fiipractic.twitter.model.LogInForm;
import com.fiipractic.twitter.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    void registerUser(User user);

    User getUserById(String id);

    User getUserByEmail(String email);

    void authenticateUser(LogInForm logInForm);

    void followUser(String credential, String followCredential);
    void unfollowUser(String credential, String followCredential);
    boolean isFollowing(String credential, String followCredential);
    int getFollowingNumber(String credential);
    int getFollowerNumber(String credential);
    List<String> getUsersListFollowedBy(String credential) throws Exception;
    List<String> getUsersListFollowing(String credential) throws  Exception;

}
