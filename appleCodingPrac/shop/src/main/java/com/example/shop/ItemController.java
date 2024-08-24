// 파일을 하나 만들면 이게 자동 생성(인텔리제이)
// 아래의 클래스를 다른 파일에서 가져다 쓰고 싶으면 이 파일의 경로를 아래와 같이 적어놔야함.
// package로 시작~ com 폴더 안 example 폴더 안 shop 폴더 안에 있는 것. 그 뜻이야 아래가.
package com.example.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
    String list(Model model){

        List<Item> result = itemRepository.findAll(); // list형으로 갖고옴.
        model.addAttribute("items", result);

        var a = new Item();
        System.out.println(a.toString()); // Item(id=null, title=null, price=null)
        // Item.java에 @ToString 등록하고,
        // 이렇게하면 object의 값만 뽑을 수 있음.


        return "list.html";




    }
}
