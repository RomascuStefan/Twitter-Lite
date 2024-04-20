package com.fiipractic.twitter.controller;

import com.fiipractic.twitter.model.LogInForm;
import com.fiipractic.twitter.model.User;
import com.fiipractic.twitter.model.UserNumbers;
import com.fiipractic.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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

    @PostMapping(value = "/logInForm", produces = MediaType.APPLICATION_JSON_VALUE)
    public void loginUser(@RequestBody LogInForm logInForm) {
        userService.authenticateUser(logInForm);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<String> checkUserExistence(@PathVariable String userId) {
        userService.getUserById(userId);
        return ResponseEntity.ok("User exists");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<String> checkUserExistenceEmail(@PathVariable String email) {
        userService.getUserByEmail(email);
        return ResponseEntity.ok("User exists");
    }
    @PostMapping(value = "/follow/{userId}/{followingId}")
    public void followUser(@PathVariable String userId, @PathVariable String followingId){
        userService.followUser(userId,followingId);
    }
    @PostMapping(value = "/unfollow/{userId}/{followingId}")
    public void unfollowUser(@PathVariable String userId, @PathVariable String followingId){
        userService.unfollowUser(userId,followingId);
    }

    @GetMapping(value = "/isFollowing/{userId}/{followingId}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable String userId, @PathVariable String followingId){
        boolean isFollowing = userService.isFollowing(userId,followingId);
        return ResponseEntity.ok(isFollowing);
    }

    @GetMapping("/getNumbers/{credential}")
    public ResponseEntity<UserNumbers> getUserNumbers(@PathVariable String credential) {
        try {
            int followers = userService.getFollowerNumber(credential);
            int following = userService.getFollowingNumber(credential);
            UserNumbers numbers = new UserNumbers();
                numbers.setFollowers(followers);
                numbers.setFollowing(following);

            return ResponseEntity.ok(numbers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/followedByList/{credential}")
    public ResponseEntity<List<String>> getFollowedListUsers(@PathVariable String credential) {
        try {
            List<String> followedUsers = userService.getUsersListFollowedBy(credential);
            return ResponseEntity.ok(followedUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }
    }

    @GetMapping("/followingList/{credential}")
    public ResponseEntity<List<String>> getFollowingListUsers(@PathVariable String credential) {
        try {
            List<String> followedUsers = userService.getUsersListFollowing(credential);
            return ResponseEntity.ok(followedUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonList(e.getMessage()));
        }
    }


}
