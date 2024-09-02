package com.example.shop.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;
    private Integer price;

    /* lombok의 @Getter
    -> 컬럼 위에 붙여주면 아래 함수가 알아서 내부적으로 만들어주는것
    -> 아니면 전체 클래스에 붙여도 가능, Setter도 동일
    public String getTitle(){
        return title;
    }

    @Setter : 변수들의 api 만드는 곳 _ 이걸 가져다쓰는 사람은 내부 로직 신경안써도된다.
    -> 컬럼 위에 붙여주면 아래 함수가 알아서 내부적으로 만들어주는것
    public void setTitle(String title){
        if 255자 이하면 title에 저장해주세요~ 같은 로직 추가 가능->안정성 올리기
        this.title=title;
    }
*/
}
