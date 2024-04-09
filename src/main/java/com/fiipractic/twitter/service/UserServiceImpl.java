package com.fiipractic.twitter.service;

import com.fiipractic.twitter.exception.UserNotFoundException;
import com.fiipractic.twitter.model.LogInForm;
import com.fiipractic.twitter.model.User;
import com.fiipractic.twitter.repository.UserDAO;
import com.fiipractic.twitter.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    private final UserDAO userRepository;
    private final UserUtil userUtil;

    @Autowired
    public UserServiceImpl(UserDAO userRepository, UserUtil userUtil) {
        this.userRepository = userRepository;
        this.userUtil = userUtil;
    }

    @Override
    public void registerUser(User user) {
        userRepository.createUser(user.getId(), user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword());
    }

    @Override
    public User getUserById(String id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }


    @Override
    public void updateUser(String id, User user) {
        userRepository.updateUser(user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword(), id);
    }

    @Override
    public void patchUser(String id, Map<String, String> partialUser) {
        User user = userRepository.getUserById(id);

        userUtil.patchUser(user, partialUser);

        userRepository.updateUser(user.getFirst_name(), user.getLast_name(), user.getEmail(), user.getPassword(), id);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteUser(id);
    }

    @Override
    public void authenticateUser(LogInForm logInForm) {
        User user=null;
        try{
            if(logInForm.getAuthType().equals("email"))
                user=getUserByEmail(logInForm.getCredential());
            else
                user =  getUserById(logInForm.getCredential());
        }catch (UserNotFoundException ex)
        {
            throw new UserNotFoundException("User-ul nu exista");
        }

        if(!user.getPassword().equals(logInForm.getPassword()))
            throw new UserNotFoundException("User-ul nu exista");

    }
}
