package com.merchordersystem.backend.dao;

import com.merchordersystem.backend.model.User;

public interface UserDao {
    User getById(Integer userId);
}
