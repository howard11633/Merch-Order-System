package com.merchordersystem.backend.service;
import com.merchordersystem.backend.dto.UserQueryParams;
import com.merchordersystem.backend.dto.UserRequest;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import java.util.List;

public interface UserService {
    //CRUD
    User getById(Integer userId);
    Integer createUser(UserRequest userRequest);
    void updateUser(Integer userId, UserRequest userRequest);
    void deleteUser(Integer userId);

    List<User> getUsers(UserQueryParams userQueryParams);
}
