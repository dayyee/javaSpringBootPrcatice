package com.example.management.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid에서 발생하는 MethodArgumentNotValidException 처리
    // DTO 객체의 필드에 대한 유효성 검사
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handlerValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();

        // 유효성 검사에 실패한 필드와 메시지 가져옴
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // 400 Bad Request 응답
    }

    // @Validated에서 발생하는 ConstraintViolationException 처리
    // 주로 메서드 레벨에서 발생하는 유효성 검사 실패시 발생 (약간 service 쪽에서 걸어두는듯)
    // 메서드 파라미터나 리턴값에 대해 유효성 검사를 수행할 때, 검사 실패시 예외 발생
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        // 유효성 검사에 실패한 필드와 메시지를 가져옴
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST); // 400 Bad Request 응답
    }

    // 그 외 다른 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("서버 내부 오류가 발생했습니다: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
