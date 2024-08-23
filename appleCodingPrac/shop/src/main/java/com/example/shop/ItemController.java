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

    // 2. 원하는 클래스에 repository 등록

    private final ItemRepository itemRepository;

    @GetMapping("/list")
    String list(Model model){
        // 3. repository.입출력문법()쓰기
        // List 타입으로 갖고 오니까 List로 해주고,
        // <Item>은 출력중인 테이블 클래스(Entity만든거 생각)를 넣어야하기에..
        List<Item> result = itemRepository.findAll(); // list형으로 갖고옴.

        // 출력값 : [com.example.shop.Item@31a41ba0, com.example.shop.Item@5e10fde0, com.example.shop.Item@309cb571]
        System.out.println(result);

        // 첫번째 자료 뽑기. 근데 item 오프젝트에서 뽑은 로우 데이터만 나옴 : com.example.shop.Item@31a41ba0
        System.out.println(result.get(0));
        // 첫 번째 자료의 price 뽑기 : 7000
        System.out.println(result.get(0).price);


        // 여러 데이터 한 변수에 넣으려면(Array List형 선언)
        List<Integer> a = new ArrayList<>();
        a.add(30);
        a.add(40);
        System.out.println(a); // [30, 40]
        // a라는 array list의 index 0번째의 데이터 출력
        System.out.println(a.get(0)); // [30]

        // 좀 더 명확하게 ArrayList의 타입을 정확히 기재하려면
        ArrayList<Integer> b = new ArrayList<>(); // <> 안은 데이터의 타입
        List<Object> c = new ArrayList<>(); // List가 좀 더 큰 개념이라.. 저렇게도함. + Object는 여러종류의 데이터의 타입을 동시에 사용하고 싶을 때


        model.addAttribute("name","홍길동");
        return "list.html";

        /* 참고 > 롬복 문법(@RequiredArgsConstructor) 안쓰고 싶으면
        itemRepository의 constructor 만들어야함.

                                    ⬇️ new ItemRepository() 들어있고
        private final ItemRepository itemRepository;
        @Autowired
        public ItemController(ItemRepository itemRepository) {
            this.itemRepository = itemRepository;
            ⬆️  new ItemRepository() 하나 뽑아서
            itemRepository 변수에 넣으라고 시키는 중...
        }
         */
    }
}
