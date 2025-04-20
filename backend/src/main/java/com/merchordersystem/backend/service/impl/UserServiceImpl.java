package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.dao.UserDao;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getById(Integer userId) {
        return userDao.getById(userId);
    }
}
