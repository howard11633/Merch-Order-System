package com.merchordersystem.backend.service;

import com.merchordersystem.backend.model.User;

import java.util.Optional;

public interface UserService {

    void createUser(User user);
    void updateUser(User user);
    void deleteById(Integer userId);
    User getById(Integer userId);

}
