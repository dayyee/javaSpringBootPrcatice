package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;


@Controller
// @RequiredArgsConstructor // + lombok 문법(2번 단계)
public class ItemController {
    // object 알아서 뽑아서            ⬇️여기 넣어달라는 뜻..
    private final ItemRepository itemRepository; // 그래서 new itemRepository() 가 들어있는 것..
    private final ItemService itemService; // + 롬복 @RequiredArgsConstructor해야하고, 안하면 아래 @Autowired 포함한 코드 넣어야함..

    // 롬복 @RequiredArgsConstructor 안쓰면 아래 코드..
    @Autowired
    public ItemController(ItemRepository itemRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    // 이런식으로 new 키워드로 소스 코드 안에서 오브젝트 직접 사용하는게 아니라
    // 다른 곳에서 오브젝트 뽑은다음에 다른 곳에서 변수로 가져다 쓰는 것을
    // Dependency Injection 이라고 함.
    // 사용 이유?
    // 1. object 여러개 안뽑아도 되어서 효율적
    // 2. 클래스간의 커플링 줄일 수 있음.
    //    (다른 클래스에서 new키워드로 오브젝트 뽑아서 쓰면, 커플링 생김.. 나중에 오브젝트에서 변경 생기면 관리 나중에 힘들어짐..)



    @GetMapping("/list")
    String list(Model model) {

        List<Item> result = itemService.findAllItems(); // list형으로 갖고옴.
        model.addAttribute("items", result);

        return "list.html";
    }


    @PostMapping("/add")
    // String addPost(@RequestParam String title, @RequestParam Integer price){
    String addPost(String title, Integer price) throws Exception {
        // -> @ModelAttribute Item item하면 -> input데이터들을 바로 item 오브젝트로 변환해준다.
        // 이거 사용 시에는 setter 생략 가능
        // System.out.println(item);
//         Item item = new Item();
//         item.setTitle(title);
//         item.setPrice(price);
//         itemRepository.save(item);

        // 다른 class의 함수를 사용할 때
        // (X) new itemService().saveItem(String title, Integer price);
        // 오브젝트 100번이면 100번 뽑아야함..
        // 그니까 다른데서 미리 new Class()해놓고 재사용하는게 좋다. <- 스프링한테 시키자..
        // 1. new Class()할 클래스에 @Service or @Repository or @Component 붙이기
        // 2. 사용할 곳에서 변수로 등록하기. private final~
        // 3. 변수 사용

        itemService.saveItem(title, price);
        return "redirect:/list";
    }

    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    // edit-post
    @PostMapping("/edit")
    String updatePost(Long id, String title, Integer price) throws Exception {
        // 1. 먼저 id를 통해 받아온 title과 price를 화면단에 넣어줘야함.
        // 2. 그거 가지고 수정해서 다시 데이터 받아서 화면에 뿌려주기
        // 3, list로 redirect 하기
        itemService.updateItem(id, title, price);
        return "redirect:/list";

    }
    // edit-get
    @GetMapping("/edit/{id}")
    String modify(@PathVariable Long id, Model model){
        Optional<Item> result = itemService.findItemById(id);
        if(result.isPresent()){
            model.addAttribute("itemInfo", result.get());
            return "edit.html";
        }
        return "error.html";
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

        Optional<Item> result = itemService.findItemById(id);

            if(result.isPresent()) {
                model.addAttribute("itemDetail", result.get());
                return "detail.html";
            }else {
                // return "error.html";
                // error페이지 넣어도되고.. redirect:/list 해도되고
                return "";
            }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(){
        return ResponseEntity.status(400).body("다시 입력해요");
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

