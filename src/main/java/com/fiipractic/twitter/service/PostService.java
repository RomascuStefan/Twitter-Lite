package com.fiipractic.twitter.service;

import java.util.List;
import java.util.Map;

public interface PostService {
    void createPost(String user_id, String content);
    List<Map<String, Object>> getPostsByUserId(String user_id);
    List<Map<String, Object>> getPostsByUserEmail(String email);
    void likeUnlikePost(String userID, int postId);
    boolean isPostLiked(String userId, int postId);
    Integer getLikesByPostId (int postId);
    void addPost(String credential, String content);
}
