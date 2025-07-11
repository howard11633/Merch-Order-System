package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.Specification.UserSpecification;
import com.merchordersystem.backend.dao.UserDao;
import com.merchordersystem.backend.dto.user.UserLoginRequest;
import com.merchordersystem.backend.dto.user.UserQueryParams;
import com.merchordersystem.backend.dto.user.UserRegisterRequest;
import com.merchordersystem.backend.dto.user.UserRequest;
import com.merchordersystem.backend.model.Role;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    //加入log機制：slf4j logger
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        //先用email取得User
        User checkUser = userRepository.getByEmail(userRegisterRequest.getEmail());

        if (checkUser != null){
            log.warn("該email {} 已被註冊", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //生成雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        //email沒被用過，可以建立新User
        User user = new User();
        user.setName(userRegisterRequest.getName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setPassword(hashedPassword);
        user.setGender(userRegisterRequest.getGender());
        user.setRole(Role.MEMBER);

        // 存進 DB
        userRepository.save(user);

        return user.getId();
    }

    //登入時，透過email抓取使用者資料後，再去比對密碼
    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    //登入
    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = getByEmail(userLoginRequest.getEmail());

        //若尚未註冊
        if (user == null){
            log.warn("該email {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用MD5生成密碼雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());


        //判斷密碼是否正確
        if (hashedPassword.equals(user.getPassword())){
            return user;

        } else {
            log.warn("該email {} 密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

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
