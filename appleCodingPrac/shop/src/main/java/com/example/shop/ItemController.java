package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/*
DB 데이터 출력하려면
1. Repository 만들기(interface)
2. Repository 등록하기(사용하려고하는 class에)
3. repository.입출력문법() 쓰기
 */

@Controller
@RequiredArgsConstructor // + lombok 문법(2번 단계)
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/list")
    String list(Model model) {

        List<Item> result = itemRepository.findAll(); // list형으로 갖고옴.
        model.addAttribute("items", result);

        return "list.html";
    }

        // var a = new Item();
        // System.out.println(a.toString()); // Item(id=null, title=null, price=null)
        // Item.java에 @ToString 등록하고,
        // 이렇게하면 object의 값만 뽑을 수 있음.


        // 🌟 상품 추가 기능
        // 1. 상품 이름, 가격 작성 할 수 있는 페이지와 폼
        // 2. 전송 버튼 누르면 서버로 보냄
        // 3. 서버는 검사 후 이상 없으면 DB 저장

//    @GetMapping("/write")
//    String write(){
//        return "write.html";
//    }
    // Map 자료형 쓰기 : input이 100개면? 파라미터에 하나하나 값 쓰고 있을 수 없음.
    // Map 자료형은? {}로 시작하고 여러 데이터 한 변수에 넣고 싶을 때 사용. list와 비슷하다.
    @PostMapping("/add")
    // String addPost(@RequestParam String title, @RequestParam Integer price){
    String addPost(@ModelAttribute Item item){
        // Item item = new Item();
        // item.setTitle(title);
        // item.setPrice(price);
        // -> @ModelAttribute Item item하면 -> input데이터들을 바로 item 오브젝트로 변환해준다.
        // 이거 사용 시에는 setter 생략 가능
        System.out.println(item);
        itemRepository.save(item);

        return "redirect:/list";
    }
    /*
    일반으로 쓰기
    @PostMapping("/add")
    //                            ⬇️ 파라미터로 들어온 값의 타입을 변환하고자하는 타입으로 적는것.
    String addPost(@RequestParam String title, @RequestParam Integer price){
        // form태그 사용시에 @RequestParam을 사용, @RequestParam 원래 생략 가능
        // ajax 요청의 경우 @RequestBody 써야 출력 가능.
        System.out.println(title); // form tag안의 input tag, name="title"에 입력한 값 출력됨.
        System.out.println(price); // form tag안의 input tag, name="price"에 입력한 값 출력됨.

        return "redirect:/list"; // redirect 시키기
    }
    */

    // URL 파라미터 문법 쓰기
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) { // @PathVariable paramType paramName : id값 가져오기.
                    Optional<Item> result = itemRepository.findById(id);

            if(result.isPresent()) {
                model.addAttribute("itemDetail", result.get());
                return "detail.html";
            }else {
                return "error.html";
                // error페이지 넣어도되고.. redirect:/list 해도되고
            }
    }
    }

        // REST API 서버 에러 처리
        // 1. try~catch 문법
//        try{
//            Optional<Item> result = itemRepository.findById(id);
//
//            if(result.isPresent()) {
//                model.addAttribute("itemDetail", result.get());
//                return "detail.html";
//            }else {
//                return "error.html";
//                // error페이지 넣어도되고.. redirect:/list 해도되고
//            }
//        }catch(Exception e){
//            System.out.println((e.getMessage()));
//            return "";
//            // 개발할 때나 유용 -> 실제 배포시 logging 라이브러리 사용..
//        }

    // 2. 모든 API의 에러를 캐치하려면, @ExceptionHandler(Exception.class) 사용
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String>  handler(){
//        return ResponseEntity.status(400).body("너의 잘못");
//    }
    // 또는 3. 전역에서 다 에러 뱉어내기 하기
    //         1) 파일 하나 만들고(여기서는 MyExceptionHandler.java 로..)
    //         2) 그 클래스 위에 @ControllerAdvice 붙이기)

