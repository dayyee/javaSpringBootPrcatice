package com.example.management.controller;


import com.example.management.aws.AwsSqsService;
import com.example.management.model.SubjectDTO;
import com.example.management.model.UserDTO;
import com.example.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        this.userService = userService;
    }
    */

    // 사용자 전체 조회
    @GetMapping("/all")
    public List<UserDTO> findAllUsers(){
        return userService.findAllUsers();
    }

    // id로 사용자 조회
    @GetMapping("/{id}")
    public UserDTO findUserById(@PathVariable("id") Integer id){
    return userService.findUserById(id);
    }

    // id로 subject table까지 조회
    @GetMapping("/detail/{id}")
    public SubjectDTO findSubjectById(@PathVariable("id") Integer id){
        return userService.findSubjectById(id);
    }

    // id로 사용자 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUserById(@PathVariable("id") Integer id, @RequestBody UserDTO formData) {
        Integer updateCnt = userService.updateUserById(formData);
        if (updateCnt > 0) {
            return ResponseEntity.ok("사용자 정보가 수정되었습니다.");
        } else {
            return ResponseEntity.status(404).body("사용자 정보가 없습니다.");
        }
    }

    // id로 사용자 삭제 후 메시징 처리
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeUserById(@PathVariable("id") Integer id){
        Integer removeCnt = userService.removeUserById(id);
        if(removeCnt > 0){
            return ResponseEntity.ok("사용자 정보가 삭제되었습니다.");
        } else {
            return ResponseEntity.status(404).body("사용자 정보가 없습니다.");
        }
    }
}