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

    public int createUser(String id, String firstName, String  lastName, String email, String password) {
        try {
            return jdbcTemplate.update("INSERT INTO USERS(id, first_name, last_name, email, password) VALUES (?, ?, ?, ?, ?)",
                    id, firstName, lastName, email, password);
        }catch (DuplicateKeyException ex){
            String out=id;
            String message="Username already used: ";
            String err=ex.getMessage();
            if(err.contains(email))
            {
                out=email;
                message="Email already used: ";
            }
            throw new UserAlreadyExistException(message + out);
        }
    }

    public int updateUser(String firstName, String  lastName, String email, String password, String id) {
        return jdbcTemplate.update("UPDATE USERS SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PASSWORD = ? WHERE ID = ?",
                firstName, lastName, email, password, id);
    }

    public int deleteUser(String id) {
        return jdbcTemplate.update("DELETE FROM USERS WHERE ID = ?", id);
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

}
