package com.madthreed.userAuth.services;

import com.madthreed.userAuth.domain.User;
import com.madthreed.userAuth.handlers.UserServiceException;

import java.util.List;
import java.util.Map;

public interface UserService {
    void registerNewUser(User user) throws UserServiceException;

    void updateUser(User user, Map<String, String> form);

    List<User> getAllUsers();

    void deleteUser(User user);

    void sendNewCredentials(User user) throws UserServiceException;

    void activateUserByToken(String token) throws UserServiceException;
}
