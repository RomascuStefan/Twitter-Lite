package com.fiipractic.twitter.service;

import com.fiipractic.twitter.repository.MainPageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FollowingServiceImpl implements FollowingService {
    private final MainPageDAO mainPageRepository;

    @Autowired
    public FollowingServiceImpl(MainPageDAO mainPageRepository) {
        this.mainPageRepository = mainPageRepository;
    }

    @Override
    public List<Map<String, Object>> getMainPagePostWithID(String user_id) {
        return mainPageRepository.getPostsForMainPageWithID(user_id);
    }

    @Override
    public List<Map<String, Object>> getMainPagePostWithEmail(String email) {
        return  mainPageRepository.getPostsForMainPageWithEmail(email);
    }
}
