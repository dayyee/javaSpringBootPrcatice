package com.example.shop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class MyExceptionHandler {
    // 이거 모든 에러를 말하는것 : Exception.class
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(){
        return ResponseEntity.status(400).body("전역 에러");
    }

    // argument의 입력값이 다를 때 나는 에러로 하고 싶으면 아래와 같이 바꾸기.. 저렇게 찾아서 바꾸면되겠다.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handler2(){
        return ResponseEntity.status(400).body("타입 에러");
    }
}

