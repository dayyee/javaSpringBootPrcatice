package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result= repository.findById(member.getId()).get();
        // 이렇게 글자로 계속 볼순 없다. System.out.println("result = " + (result == member));

        // Assertions.assertEquals(member,result);
        // option + enter 치면 스태틱 임포트할 수 있다. 그러면서 Assertions를 생략 가능.
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);


        // shift + F6 이름 바꾸기
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        // Optional<Member> result = repository.findByName("spring1"); 아래와 같은 것..
        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);

    }

    @Test
    public void findByAll(){
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);


    }

}

