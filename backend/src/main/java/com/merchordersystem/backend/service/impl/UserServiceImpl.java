package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.dao.UserDao;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    //增
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    //刪
    @Override
    public void deleteById(Integer userId) {
        userRepository.deleteById(userId);
    }

    //改
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    //查
    @Override
    public User getById(Integer userId) {
        return userRepository.findById(userId).orElse(null);//沒有.orElse會出錯
    }
}
