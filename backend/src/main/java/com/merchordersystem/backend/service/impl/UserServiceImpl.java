package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.Specification.UserSpecification;
import com.merchordersystem.backend.dao.UserDao;
import com.merchordersystem.backend.dto.UserQueryParams;
import com.merchordersystem.backend.dto.UserRequest;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    //增
    @Override
    public Integer createUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setGender(userRequest.getGender());
        user.setRole(userRequest.getRole());
        // 存進 DB
        userRepository.save(user);

        // 回傳主鍵 ID（或你要回傳 DTO 都可）
        return user.getId();
    }

    //刪
    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    //改
    @Override
    public void updateUser(Integer userId, UserRequest userRequest) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setGender(userRequest.getGender());
            user.setRole(userRequest.getRole());
            user.setPassword(userRequest.getPassword());
            userRepository.save(user);
        }
    }

    //查
    @Override
    public User getById(Integer userId) {
        return userRepository.findById(userId).orElse(null);//沒有.orElse會出錯
    }

    @Override
    public List<User> getUsers(UserQueryParams userQueryParams) {
        // role跟search沒在這用到
//        Role role = userQueryParams.getRole();
//        String search = userQueryParams.getSearch();


//        String orderBy = userQueryParams.getOrderBy();
//        String sort = userQueryParams.getSort();
//        Integer limit = userQueryParams.getLimit();
//        Integer offset = userQueryParams.getOffset();

        String orderBy = userQueryParams.getOrderBy() != null ? userQueryParams.getOrderBy() : "created_at";
        String sort = userQueryParams.getSort() != null ? userQueryParams.getSort() : "desc";
        int limit = (userQueryParams.getLimit() != null && userQueryParams.getLimit() > 0) ? userQueryParams.getLimit() : 8;
        int offset = (userQueryParams.getOffset() != null && userQueryParams.getOffset() >= 0) ? userQueryParams.getOffset() : 0;

        Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by(direction, orderBy));
        Specification<User> spec = UserSpecification.build(userQueryParams); // Filtering
        Page<User> springPage = userRepository.findAll(spec, pageable);

//        // 如果完全沒參數 → 全部使用者
        return springPage.getContent();
    }

    @Override
    public Long countUser(UserQueryParams userQueryParams) {
        Specification<User> spec = UserSpecification.build(userQueryParams); // Filtering
        return userRepository.count(spec);
    }
}
