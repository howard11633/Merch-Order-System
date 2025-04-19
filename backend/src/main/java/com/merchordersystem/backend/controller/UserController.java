package com.merchordersystem.backend.controller;

import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public String insert(@RequestBody User user){

        userRepository.save(user);

        return "執行Create";
    }


}
