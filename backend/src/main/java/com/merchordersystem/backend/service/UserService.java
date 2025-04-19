package com.merchordersystem.backend.service;

import com.merchordersystem.backend.model.User;

public interface UserService {
    User getById(Integer userId);
}
