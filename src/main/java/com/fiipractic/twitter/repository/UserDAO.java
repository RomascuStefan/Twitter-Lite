package com.fiipractic.twitter.repository;


import com.fiipractic.twitter.exception.UserAlreadyExistException;
import com.fiipractic.twitter.exception.UserNotFoundException;
import com.fiipractic.twitter.model.User;
import com.fiipractic.twitter.repository.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(final DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createUser(String id, String firstName, String lastName, String email, String password) {
        try {
            return jdbcTemplate.update("INSERT INTO USERS(id, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)",
                    id, firstName, lastName, email, password);
        } catch (DuplicateKeyException ex) {
            String out = id;
            String message = "Username already used: ";
            String err = ex.getMessage();
            if (err.contains(email)) {
                out = email;
                message = "Email already used: ";
            }
            throw new UserAlreadyExistException(message + out);
        }
    }

    public User getUserById(String id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE ID = ?", new UserRowMapper(), id);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException(String.format("User with id %s was not found", id));
        }
    }

    public User getUserByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE email = ?", new UserRowMapper(), email);
        } catch (EmptyResultDataAccessException ex) {
            throw new UserNotFoundException(String.format("User with email %s was not found", email));
        }
    }


    public void followUser(String credential, String followCredential) {
        String userId = getUserByCredential(credential);
        String followId = getUserByCredential(followCredential);

        jdbcTemplate.update("UPDATE `users` SET `following_number` = `following_number` +1 WHERE (`id` = ?)", userId);
        jdbcTemplate.update("UPDATE `users` SET `followers_number` = `followers_number` +1 WHERE (`id` = ?)", followId);

        jdbcTemplate.update("INSERT INTO `following` (`user_id`, `follower_id`) VALUES (? , ?)", userId, followId);
    }

    public void unfollowUser(String credential, String followCredential) {
        String userId = getUserByCredential(credential);
        String followId = getUserByCredential(followCredential);


        jdbcTemplate.update("UPDATE `users` SET `following_number` = `following_number` -1 WHERE (`id` = ?)", userId);
        jdbcTemplate.update("UPDATE `users` SET `followers_number` = `followers_number` -1 WHERE (`id` = ?)", followId);

        jdbcTemplate.update("DELETE FROM `following` WHERE (`user_id` = ?) and (`follower_id` = ?)", userId, followId);
    }

    public boolean isFollowing(String credential, String followCredential) {
        String userId = getUserByCredential(credential);
        String followId = getUserByCredential(followCredential);


        String sql = "SELECT COUNT(*) FROM `following` WHERE `user_id` = ? AND `follower_id` = ?";

        int count = jdbcTemplate.queryForObject(sql, new Object[]{userId, followId}, Integer.class);
        return count > 0;
    }

    public int getFollowerNumber(String credential) {
        String userId = getUserByCredential(credential);
        return jdbcTemplate.queryForObject("SELECT `followers_number` FROM `users` WHERE id=?", new Object[]{userId}, Integer.class);
    }

    public int getFollowingNumber(String credential) {
        String userId = getUserByCredential(credential);
        return jdbcTemplate.queryForObject("SELECT `following_number` FROM `users` WHERE id=?", new Object[]{userId}, Integer.class);

    }

    public List<String> getUsersListFollowed(String credential) throws Exception {
        String userId = getUserByCredential(credential);

        String sql = "SELECT `user_id` FROM `following` WHERE `follower_id` = ?";
        List<String> usersList= jdbcTemplate.queryForList(sql, new Object[]{userId}, String.class);

        if (usersList.isEmpty()) {
            throw new Exception("User not followed by anyone.");
        }
        return  usersList;
    }

    public List<String> getUsersListFollowing(String credential) throws Exception {
        String userId = getUserByCredential(credential);

        String sql = "SELECT follower_id FROM following WHERE user_id = ?";
        List<String> usersList= jdbcTemplate.queryForList(sql, new Object[]{userId}, String.class);

        if (usersList.isEmpty()) {
            throw new Exception("User not following anyone.");
        }
        return  usersList;
    }

    private String getUserByCredential(String credential) {
        User user = null;
        if (credential.contains("@"))
            user = getUserByEmail(credential);
        else
            user = getUserById(credential);

        return user.getId();
    }

}
