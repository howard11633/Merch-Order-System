package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.dao.UserDao;
import com.merchordersystem.backend.dto.UserRequest;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    //增
    @Override
    public Integer createUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setGender(userRequest.getGender());
        user.setRole(userRequest.getRole());
        // 存進 DB
        userRepository.save(user);

        // 回傳主鍵 ID（或你要回傳 DTO 都可）
        return user.getId();
    }

    //刪
    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    //改
    @Override
    public void updateUser(Integer userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setGender(userRequest.getGender());
            user.setRole(userRequest.getRole());
            user.setPassword(userRequest.getPassword());
            userRepository.save(user);
        }
    }

    //查
    @Override
    public User getById(Integer userId) {
        return userRepository.findById(userId).orElse(null);//沒有.orElse會出錯
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }
}
