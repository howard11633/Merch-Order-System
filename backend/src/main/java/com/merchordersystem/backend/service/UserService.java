package com.merchordersystem.backend.service;

import com.merchordersystem.backend.dto.UserRequest;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    //CRUD
    User getById(Integer userId);
    Integer createUser(UserRequest userRequest);
    void updateUser(Integer userId, UserRequest userRequest);
    void deleteUser(Integer userId);

    List<User> getUsers();
    List<User> getUsersByRole(Role role);

}
