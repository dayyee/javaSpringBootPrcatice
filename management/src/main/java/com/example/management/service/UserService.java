package com.example.management.service;

import com.example.management.UserMapper;
import com.example.management.aws.AwsSqsService;
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
    private final AwsSqsService awsSqsService;

    public UserService(UserMapper userMapper, AwsSqsService awsSqsService){
        this.userMapper=userMapper;
        this.awsSqsService=awsSqsService;
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

    public Integer removeUserById(Integer id) {
        UserDTO userDTO= userMapper.findUserById(id);

        if(userDTO == null){
            return 0;
        }

        Integer result = userMapper.removeUserById(id);

        if(result > 0){
            awsSqsService.sendMessage(userDTO);
        }

        return result;
    }

}
