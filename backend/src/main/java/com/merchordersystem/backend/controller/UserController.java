package com.merchordersystem.backend.controller;

import com.merchordersystem.backend.dto.UserRequest;
import com.merchordersystem.backend.model.Product;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.service.UserService;
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

    //新增使用者
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

    //查詢所有使用者
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(required = false) Role role //以此決定要查哪種類型的使用者(ADMIN or MEMBER)
    ){
        List<User> userList;

        if (role != null){
            userList = userService.getUsersByRole(role);
        }
        else {
            userList = userService.getUsers();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userList);//無論有無查到，都回傳OK
    }

}
