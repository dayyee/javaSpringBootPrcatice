package com.example.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.ZonedDateTime;

// import java.util.Date;
// 메인페이지 접속시 "안녕하세요" 보내주고 싶다.
// 1. 클래스에 컨트롤러라는 어노테이션을 붙인다.
// 이렇게 생성된 클래스 안에서 서버기능 제작 가능하다.
// 이렇게 컨트롤러 어노테이션을 붙이면, 알아서 spring이 챙겨줌.
// (알아서 application파일로 가져다가 실행해준다)
// 2. 그 컨트롤러 어노테이션이 붙은 클래스 안에 >>
// 2-1. @GetMapping("url")
// 2-2. @ResponseBody  붙이고
// : 뜻_아래 함수의 return 값을 그대로 보내주세요라는 뜻.
// 3. 그 아래에 보여주고싶은 로직 짜기
@Controller
public class BasicController {
    @GetMapping("/")
    @ResponseBody
    String hello() {
        return "안녕하세요.";
    }

    @GetMapping("/about")
    @ResponseBody
    String about() {
        return "스프링부트 페이지에요.";
    }

    // html 보여주려면?
    // 1. 직접 하드코딩
    @GetMapping("/mypage1")
    @ResponseBody
    String mypage1() {
        return "<h4>하드코딩 마이페이지입니다.</h4>";
    }
    // 2. 파일로 만들어서 import해오기
    @GetMapping("/mypage")
    String mypage(){
        // html 파일 경로 적기
        // resources/statica이 기본 경로로 잡혀있음.
        return "index.html";
    }

    // 오늘의 숙제
    // /date로 접속하면 현재 날짜와 시간을 보내주는 기능
    @GetMapping("/date")
    @ResponseBody
    String date(){
        return ZonedDateTime.now().toString();
    }
//    String date(){
//        Date today = new Date();
//        // System.out.println(today);
//        return "";
//    }

}


