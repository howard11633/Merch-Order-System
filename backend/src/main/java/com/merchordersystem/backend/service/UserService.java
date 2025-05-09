package com.merchordersystem.backend.service;
import com.merchordersystem.backend.dto.UserQueryParams;
import com.merchordersystem.backend.dto.UserRegisterRequest;
import com.merchordersystem.backend.dto.UserRequest;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import java.util.List;

public interface UserService {
    //一般使用者註冊
    Integer register(UserRegisterRequest userRegisterRequest);

    //CRUD
    User getById(Integer userId);
    Integer createUser(UserRequest userRequest);
    void updateUser(Integer userId, UserRequest userRequest);
    void deleteUser(Integer userId);

    //動態查詢使用者用
    List<User> getUsers(UserQueryParams userQueryParams);

    //用數量算出分頁頁碼總數
    Long countUser(UserQueryParams userQueryParams);
}
