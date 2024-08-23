package com.example.shop;

import jakarta.persistence.*;

@Entity
public class Item {
    // 변수에 public 붙이면 다른 모든 클래스에서 문제 없이 사용 가능.

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // @Column()으로 컬럼에 제약 설정 가능
    // 후에 이거 추가한다고 테이블 내용을 바꿔주지 않음. 그렇기에 드랍하고 만들어야함~
    // @Column(nullable = false, unique = true)
    public String title; //컬럼
    public Integer price;

}
