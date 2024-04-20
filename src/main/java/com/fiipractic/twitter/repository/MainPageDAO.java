package com.fiipractic.twitter.repository;

import com.fiipractic.twitter.model.User;
import com.fiipractic.twitter.service.PostService;
import com.fiipractic.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MainPageDAO {
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;



    @Autowired
    public MainPageDAO(JdbcTemplate jdbcTemplate, PostService postService, UserService userService, UserService userService1)
    {
        this.jdbcTemplate=jdbcTemplate;
        this.userService = userService1;
    }

    public List<Map<String, Object>> getPostsForMainPageWithID(String userId) {
        return jdbcTemplate.queryForList("SELECT p.* FROM following f JOIN posts p " +
                "ON f.follower_id=p.user_id " +
                "WHERE f.user_id= ? ORDER BY p.time_post DESC",userId);
    }

    public List<Map<String, Object>> getPostsForMainPageWithEmail(String email) {
        User user=userService.getUserByEmail(email);
        return getPostsForMainPageWithID(user.getId());
    }





}

