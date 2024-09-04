package com.example.management.controller;


import com.example.management.model.User;
import com.example.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:8090")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    /* constructor
    public UserController(UserService userService){
        this.userService = userService;ㅃQㅃ
    }
    */

    // 사용자 전체 조회
    @GetMapping("/all")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    // id로 사용자 조회
    @GetMapping("/{id}")
    public List<User> findUserById(@PathVariable("id") Integer id){
    return userService.findUserById(id);
    }
    @PutMapping("/{id}")
    public void updateUserById(@PathVariable("id") Integer id, @RequestBody Map<String, Object> formData){
        userService.updateUserById(id, formData);
    }

}
