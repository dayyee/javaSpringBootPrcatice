package com.example.management.service;

import com.example.management.UserMapper;
import com.example.management.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public List<User> findAllUsers(){
        return userMapper.findAll();
    }

    public List<User> findUserById(Integer id){
        return userMapper.findUserById(id);
    }

    public void updateUserById(Integer id, Map<String, Object>formData){
        userMapper.updateUserById(id, formData);
    }
}
