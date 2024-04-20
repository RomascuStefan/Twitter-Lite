package com.fiipractic.twitter.controller;

import com.fiipractic.twitter.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private final PostService postService;

    @Autowired
    public LikeController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping(value = "/likeUnlikePost/{credential}/{postID}")
    public void likeUnlikePost(@PathVariable String credential, @PathVariable int postID){
        postService.likeUnlikePost(credential,postID);
    }

    @GetMapping(value = "/isPostLiked/{credential}/{postID}")
    public ResponseEntity<Boolean> isPostLiked(@PathVariable String credential, @PathVariable int postID) {
        boolean isLiked = postService.isPostLiked(credential, postID);
        return ResponseEntity.ok(isLiked);
    }



}