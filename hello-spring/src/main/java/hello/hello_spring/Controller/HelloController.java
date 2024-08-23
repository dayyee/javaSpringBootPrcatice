package hello.hello_spring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data","hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    // @RequestParam 관련 : http://localhost:8080/hello-mvc?name=spring!
    public String helloMvc(@RequestParam("name") String name,Model model){
        model.addAttribute("name",name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return "hello"+name;
        // /hello-string?name=spring!으로 하면 hello spring!으로 바로 내려감.(데이터 그대로 내려감)
        // 위 hello-mvc처럼 html을 타지 않는다.
        // 사실 이 방법은 잘 사용안함.
    }

    // 진짜는 아래
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        // 이건 Hello라는 클래스를 복제한 오브젝트(객체)
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 이러면 json으로 화면 출력. {"name":"입력값"}
    }

    // 이렇게 static class를 만들면 전체 클래스 내의 이 클래스를 또 쓸 수 있다. HelloController.Hello 이렇게..?
    static class Hello{
        private String name;

        // getter 와 setter
        // JavaBean 규약(표준 양식), 프로퍼티 접근 방식
        // 이 Hello클래스 안의 name변수는 private로 되어있어서 외부에서 못꺼낸다.
        // 그래서 라이브러리 같은데서 쓰거나 내가 사용할 때도
        // 아래 메서드를 통해서 접근을 하게함.
        // 둘다 public으로 열고 있다.
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
