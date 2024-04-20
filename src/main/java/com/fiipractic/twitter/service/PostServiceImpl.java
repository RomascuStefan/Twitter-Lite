package com.fiipractic.twitter.service;

import com.fiipractic.twitter.repository.PostDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
    private final PostDAO postRepository;

    @Autowired
    public PostServiceImpl(PostDAO postRepository)
    {
        this.postRepository=postRepository;
    }

    @Override
    public void createPost(String user_id, String content) {
        postRepository.createPost(user_id,content);
    }

    @Override
    public List<Map<String, Object>> getPostsByUserId(String user_id) {
        return postRepository.getPostsByUser(user_id);
    }

    @Override
    public List<Map<String, Object>> getPostsByUserEmail(String email) {
        return postRepository.getPostsByEmail(email);
    }

    @Override
    public void likeUnlikePost(String credential, int postId) {
        postRepository.likeUnlikePost(credential, postId);
    }

    @Override
    public boolean isPostLiked(String userId, int postId) {
        return postRepository.isPostLikedByUser(userId,postId);
    }

    @Override
    public Integer getLikesByPostId(int postId) {
        return  postRepository.getLikesCount(postId);
    }

    @Override
    public void addPost(String credential, String content) {
        postRepository.addPost(credential,content);
    }


}
