package com.example.management.controller;


import com.example.management.model.User;
import com.example.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public List<User> findAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping("/{id}")
    public List<User> findUserById(@PathVariable Integer id, @RequestParam Map<String, Obejct> formData){
        return userService.findUserById(id, formData);
    }

}
