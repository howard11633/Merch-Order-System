package com.merchordersystem.backend.controller;

import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public String insert(@RequestBody User user){
        userRepository.save(user);
        return "執行Create";
    }

    @GetMapping("/users/{userId}")
    public User get(@PathVariable Integer userId){
        return userService.getById(userId);
    }


}
