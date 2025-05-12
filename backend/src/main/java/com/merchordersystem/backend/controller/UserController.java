package com.merchordersystem.backend.controller;

import com.merchordersystem.backend.dto.UserLoginRequest;
import com.merchordersystem.backend.dto.UserQueryParams;
import com.merchordersystem.backend.dto.UserRegisterRequest;
import com.merchordersystem.backend.dto.UserRequest;
import com.merchordersystem.backend.model.Product;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.service.UserService;
import com.merchordersystem.backend.util.Page;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//1.0 使用者模組
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // (一般使用者)註冊
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){

        Integer userId = userService.register(userRegisterRequest);
        User user = userService.getById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //登入 (要用RequestBody傳資料)
    @PostMapping("/users/login")
    public ResponseEntity<User> login (@RequestBody @Valid UserLoginRequest userLoginRequest){
        //用User object接
        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //（後台）新增使用者
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userRequest){

        Integer userId = userService.createUser(userRequest);
        User user = userService.getById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //修改使用者資料
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId,
                                           @RequestBody @Valid UserRequest userRequest){
        User user = getUser(userId);

        if (user != null) {
            userService.updateUser(userId, userRequest);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //刪除使用者
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //查詢單一使用者
    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable Integer userId){
        return userService.getById(userId);
    }

    //動態查詢使用者
    @GetMapping("/users")
    public ResponseEntity<Page<User>> getUsers(
            //查詢條件 Filtering
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) String search,
            //排序 Sorting
            @RequestParam(defaultValue = "name") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,
            //分頁 Pagination
            @RequestParam(defaultValue = "8") Integer limit, //前幾筆資料
            @RequestParam(defaultValue = "0") Integer offset //跳過幾筆資料
            ){

        UserQueryParams userQueryParams = new UserQueryParams();
        userQueryParams.setRole(role);
        userQueryParams.setSearch(search);
        userQueryParams.setOrderBy(orderBy);
        userQueryParams.setSort(sort);
        userQueryParams.setLimit(limit);
        userQueryParams.setOffset(offset);

        List<User> userList = userService.getUsers(userQueryParams);

        Long total = userService.countUser(userQueryParams);

        //以頁面形式回傳
        Page<User> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(userList);

        return ResponseEntity.status(HttpStatus.OK).body(page);//無論有無查到，都回傳OK
    }

}
