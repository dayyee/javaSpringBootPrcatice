package com.example.shop;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;

public class privateTest {

    private String name;

    @Getter
    @Setter
    private Integer age;

    public Integer addAge(){
        return this.age+1;
    }

    public Integer validationAge(Integer age){
        if(age > 100 || age < 0){
            return null;
            // or return this.age
        };
        this.age= age;
        return this.age;
    }
    // validationAge의 답 버전이고..
    // return값 아무것도 없게하려면 void로 써야함.
    public void validationAge2(Integer a){
        if (a > 0 && a < 100){
            this.age = a;
        }
    }

    // 실행하기 위해 main 이거 써줘야함.
    public static void main(String[] args) {
        var test = new privateTest();
        test.setAge(1);
        System.out.println("초기값 : " + test.getAge()); // 초기값 : 1

        System.out.println("+1 : " + test.addAge()); // +1 : 2
        test.setAge(12);
        System.out.println("12 : "+test.getAge()); // 12 : 12
        test.validationAge(-10);
        System.out.println("-10 : "+test.getAge()); // -10 : 12
    }

}


