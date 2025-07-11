package com.merchordersystem.backend.service;
import com.merchordersystem.backend.dto.user.UserLoginRequest;
import com.merchordersystem.backend.dto.user.UserQueryParams;
import com.merchordersystem.backend.dto.user.UserRegisterRequest;
import com.merchordersystem.backend.dto.user.UserRequest;
import com.merchordersystem.backend.model.User;
import java.util.List;

public interface UserService {
    //一般使用者註冊
    Integer register(UserRegisterRequest userRegisterRequest);
    User getByEmail(String email);
    User login(UserLoginRequest userLoginRequest);
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
