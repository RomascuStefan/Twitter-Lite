package com.fiipractic.twitter.repository;

import com.fiipractic.twitter.exception.UserHasNoPostException;
import com.fiipractic.twitter.model.User;
import com.fiipractic.twitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PostDAO {
    private final JdbcTemplate jdbcTemplate;
    private final UserService userService;

    @Autowired
    public PostDAO(JdbcTemplate jdbcTemplate, UserService userService) {
        this.jdbcTemplate = jdbcTemplate;
        this.userService = userService;
    }

    public int createPost(String user_id, String content) {
        return jdbcTemplate.update("INSERT INTO posts(user_id, content) VALUES (?, ?)", user_id, content);
    }

    public List<Map<String, Object>> getPostsByUser(String user_id) {
        try {
            return jdbcTemplate.queryForList("SELECT * FROM posts WHERE user_id=?", user_id);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserHasNoPostException(String.format("Userul %s nu are postari inca.", user_id));
        }
    }

    public List<Map<String, Object>> getPostsByEmail(String email) {
        try {
            return jdbcTemplate.queryForList("SELECT p.* FROM posts p JOIN users u ON p.user_id = u.id WHERE u.email=?", email);

        } catch (EmptyResultDataAccessException ex) {
            throw new UserHasNoPostException(String.format("Userul %s nu are postari inca.", email));
        }
    }

    public void likeUnlikePost(String credential, int postId) {
        User user = null;
        if (credential.contains("@"))
            user = userService.getUserByEmail(credential);
        else
            user = userService.getUserById(credential);
        if (user == null)
            return;

        if (isPostLikedByUser(user.getId(), postId))
            unlikePost(user.getId(), postId);
        else
            likePost(user.getId(), postId);

    }

    private void likePost(String credential, int postId) {
        jdbcTemplate.update("UPDATE `posts` SET `like_count`= `like_count` +1  WHERE id= ?", postId);
        jdbcTemplate.update("INSERT INTO `likes` (`user_id` , `post_id`) VALUES (? , ?)", credential, postId);
    }

    private void unlikePost(String credential, int postId) {
        jdbcTemplate.update("UPDATE `posts` SET `like_count`= `like_count` -1  WHERE id= ?", postId);
        jdbcTemplate.update("DELETE FROM `likes` WHERE `user_id`= ? AND `post_id`= ?", credential, postId);
    }

    public boolean isPostLikedByUser(String credential, int postId) {
        User user = null;
        if (credential.contains("@"))
            user = userService.getUserByEmail(credential);
        else
            user = userService.getUserById(credential);
        if (user == null)
            return false;

        String sql = "SELECT COUNT(*) FROM `likes` WHERE `user_id` = ? AND `post_id` = ?";

        int count = jdbcTemplate.queryForObject(sql, new Object[]{user.getId(), postId}, Integer.class);
        return count > 0;
    }

    public Integer getLikesCount(int postId) {

        String sql = "SELECT like_count FROM posts WHERE id = ?";
        Integer likesCount = jdbcTemplate.queryForObject(sql, new Object[]{postId}, Integer.class);
        return likesCount;
    }

    public void addPost(String credential, String content)
    {
        User user = null;
        if (credential.contains("@"))
            user = userService.getUserByEmail(credential);
        else
            user = userService.getUserById(credential);

        jdbcTemplate.update("INSERT INTO `posts` (`user_id`, `content`) VALUES (?, ?)",user.getId(),content);
    }


}
