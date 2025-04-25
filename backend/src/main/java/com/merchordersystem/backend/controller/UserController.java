package com.merchordersystem.backend.controller;

import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.service.UserService;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public String insert(@RequestBody User user){
        userService.createUser(user);
        return "執行Create";
    }

    @DeleteMapping("/users/{userId}")
    public String delete(@PathVariable Integer userId){
        userService.deleteById(userId);
        return "執行Delete";
    }

    @PutMapping("/users/{userId}")
    public String update(@PathVariable Integer userId,
                         @RequestBody User user){

        User u = get(userId);

        if (u != null) {

            u.setName(user.getName());
            u.setPassword(user.getPassword());
            u.setGender(user.getGender());

            userService.updateUser(u);

            return "執行Update";
        }
        else {
            return "執行Update失敗，找不到該id資料";
        }




    }

    @GetMapping("/users/{userId}")
    public User get(@PathVariable Integer userId){
        return userService.getById(userId);
    }


}
