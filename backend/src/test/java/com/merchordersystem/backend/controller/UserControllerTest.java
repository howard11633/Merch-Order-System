package com.merchordersystem.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merchordersystem.backend.dto.user.UserRegisterRequest;
import com.merchordersystem.backend.dto.user.UserRequest;
import com.merchordersystem.backend.model.Gender;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private List<User> testUsers;

    // 建立測試使用者用
    private User createTestUser(String email,
                                String password,
                                String name,
                                Role role,
                                Gender gender) {

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setGender(gender);
        user.setRole(role);
        userRepository.save(user);

        return user;
    }

    // 建立測試使用者 via API call
    private User createTestUserByRequest(String email,
                                         String password,
                                         String name,
                                         Role role,
                                         Gender gender) {

        UserRequest request = new UserRequest();
        request.setEmail(email);
        request.setPassword(password);
        request.setName(name);
        request.setRole(role);
        request.setGender(gender);

        Integer userId = userService.createUser(request);
        return userService.getById(userId);
    }

    @BeforeEach
    public void setup(){

        userRepository.deleteAll(); // 清空資料庫表
        testUsers = new ArrayList<>();

        User user1 = createTestUser("test1@gmail.com","test1", "Amy", Role.ADMIN, Gender.FEMALE);
        User user2 = createTestUser("test2@gmail.com","test2", "Bob", Role.ADMIN, Gender.MALE);
        User user3 = createTestUser("test3@gmail.com","test3", "Cat", Role.MEMBER, Gender.FEMALE);
        User user4 = createTestUser("test4@gmail.com","test4", "Dog", Role.MEMBER, Gender.MALE);
        User user5 = createTestUser("test5@gmail.com","test5", "Egg", Role.MEMBER, Gender.MALE);

        testUsers.add(user1);
        testUsers.add(user2);
        testUsers.add(user3);
        testUsers.add(user4);
        testUsers.add(user5);

    }

    @Test
    public void register_success() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test6@gmail.com");
        userRegisterRequest.setPassword("test6");
        userRegisterRequest.setName("Fan");
        userRegisterRequest.setGender(Gender.MALE);

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.email", equalTo("test6@gmail.com")))
                .andExpect(jsonPath("$.createdAt", notNullValue()));

        // 確保密碼有加密
        User user = userRepository.getByEmail(userRegisterRequest.getEmail());
        assertNotEquals(userRegisterRequest.getEmail(), user.getPassword());

    }

    @Test
    public void register_invalidEmailFormat() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test7.com");
        userRegisterRequest.setPassword("test7");
        userRegisterRequest.setName("Gay");
        userRegisterRequest.setGender(Gender.FEMALE);

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    public void register_emailAlreadyExist() throws Exception {

        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test8@gmail.com");
        userRegisterRequest.setPassword("test8");
        userRegisterRequest.setName("Han");
        userRegisterRequest.setGender(Gender.FEMALE);

        String json = objectMapper.writeValueAsString(userRegisterRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(201));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));

    }



}