// 파일을 하나 만들면 이게 자동 생성(인텔리제이)
// 아래의 클래스를 다른 파일에서 가져다 쓰고 싶으면 이 파일의 경로를 아래와 같이 적어놔야함.
// package로 시작~ com 폴더 안 example 폴더 안 shop 폴더 안에 있는 것. 그 뜻이야 아래가.
package com.example.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// api가 많아지면 비슷한 api는 모아놓는게 보기 편함.
@Controller
// public이 없으면, 같은 폴더(package) 안에서만 사용 가능
// public이 있으면, 다른 폴더(package)에서 사용 가능

// 템플릿 이용하고 싶으면 html 파일을 static폴더 말고 templates폴더에 넣어야함.
// html에 서버데이터 넣어서 보내주려면
// 1. 겟매핑한 함수 파라미터에 Model model 추가
// 2. model.addAttribute("작명","데이터")
// 3. 해당 html 파일로 가서 태그에 th:text="${작명}" 하면 데이터가 꽂힌다.
public class ItemController {
    @GetMapping("/list")
    String list(Model model){
        //model.addAttribute("전달할 데이터 이름", "데이터");
        // 전달할 데이터 이름을 쓰면 데이터가 출력됨.
        model.addAttribute("name","홍길동");
        return "list.html";
    }
}
