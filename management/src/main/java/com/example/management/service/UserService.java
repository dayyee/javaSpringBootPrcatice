package com.example.management.service;

import com.example.management.UserMapper;
import com.example.management.model.SubjectDTO;
import com.example.management.model.UserDTO;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.eventstream.MessageBuilder;

import java.util.List;

@Service
public class UserService {
    // aws SQS, SNS 등록해서 호출하기
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper=userMapper;
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
