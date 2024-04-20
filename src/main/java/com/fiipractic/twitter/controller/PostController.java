package com.fiipractic.twitter.controller;

import com.fiipractic.twitter.model.NewPost;
import com.fiipractic.twitter.model.Post;
import com.fiipractic.twitter.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/view-profile-user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getPostsByUserId(@PathVariable String userId) {
        List<Map<String, Object>> posts = postService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/view-profile-email/{email}")
    public ResponseEntity<List<Map<String, Object>>> getPostsByUserEmail(@PathVariable String email) {
        List<Map<String, Object>> posts = postService.getPostsByUserEmail(email);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/getPostLikes/{postId}")
    public ResponseEntity<Map<String, Integer>> getPostLikes(@PathVariable int postId) {
        Integer likes = postService.getLikesByPostId(postId);
        Map<String, Integer> response = Collections.singletonMap("value", likes);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-post")
    public ResponseEntity<String> addPost(@RequestBody NewPost post){
        try {
            postService.addPost(post.getCredential(),post.getContent());
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Nu este suportat formatul");
        }
    }

}
