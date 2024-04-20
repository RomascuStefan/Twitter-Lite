package com.fiipractic.twitter.controller;

import com.fiipractic.twitter.service.FollowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MainPageController {

    private final FollowingService followingService;

    public MainPageController(FollowingService followingService) {
        this.followingService = followingService;
    }

    @GetMapping("main-page-view-id/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getMainPagePostsByID(@PathVariable String userId) {
        List<Map<String, Object>> posts = followingService.getMainPagePostWithID(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("main-page-view-email/{email}")
    public ResponseEntity<List<Map<String, Object>>> getMainPagePostsByEmail(@PathVariable String email) {
        List<Map<String, Object>> posts = followingService.getMainPagePostWithEmail(email);
        return ResponseEntity.ok(posts);
    }



}
