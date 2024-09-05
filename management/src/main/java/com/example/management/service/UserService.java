package com.example.management.service;

import com.example.management.UserMapper;
import com.example.management.model.SubjectDTO;
import com.example.management.model.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public List<UserDTO> findAllUsers(){
        return userMapper.findAll();
    }

    public UserDTO findUserById(Integer id){
        return userMapper.findUserById(id);
    }

    public SubjectDTO findSubjectById(Integer id){
        return userMapper.findSubjectById(id);
    }
    public Integer updateUserById(UserDTO formData){
        return userMapper.updateUserById(formData);
    }
}
