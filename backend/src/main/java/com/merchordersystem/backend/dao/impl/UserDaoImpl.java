package com.merchordersystem.backend.dao.impl;

import com.merchordersystem.backend.dao.UserDao;
import com.merchordersystem.backend.mapper.UserRowMapper;
import com.merchordersystem.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //增
//    public String insert (User user){
//        String sql = "INSERT INTO user (:name)";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", userId);
//        List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
//
//        return "刪除";
//    }

    //增多
//    public String insertByList (User user){
//        String sql = "INSERT INTO user (:name)";
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId", userId);
//        List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());
//
//        return "刪除";
//    }

    //刪
    public String deleteById (Integer userId){
        String sql = "DELETE id, name FROM user WHERE id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        return "刪除";
    }

    //查
    public User getById(Integer userId){
        String sql = "SELECT id, name FROM user WHERE id = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<User> list = namedParameterJdbcTemplate.query(sql, map, new UserRowMapper());

        return list.isEmpty() ? null : list.get(0);
    }
}
