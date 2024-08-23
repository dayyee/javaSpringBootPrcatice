package com.example.shop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 이렇게 interface를 만들면 내부적으로 class ItemRepository도 생성해줌.
    // 근데 저 클래스 안에는 DB입출력하는 함수가 아주 많음.
    // 사용법은 아래와 같음

}
