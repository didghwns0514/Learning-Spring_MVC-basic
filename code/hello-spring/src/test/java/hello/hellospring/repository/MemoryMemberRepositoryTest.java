package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository = new MemoryMemberRepository();
    }

    @AfterEach // 실행 끝마다 실행하는 callback 메서드
    public void afterEach() {
        repository.storeClear(); // 테스트가 순서에 상관이 없게 하기위해서 완전 비우는 것
    }


    @Test
    @DisplayName("Test by id in member - True")
    public void saveByIDTrue() {
        Member member = new Member("TestMember1");
        member.setId(1L);

        // System.out.println("repository cnt : " + MemoryMemberRepository.getSequence());

        repository.save(member);
        Optional<Member> optionalMember = repository.findByID(MemoryMemberRepository.getSequence());
        Member getMember = optionalMember.get();

        Assertions.assertThat(getMember).isInstanceOf(Member.class);
    }

    @Test
    @DisplayName("Test by name in member")
    public void saveByNameTrue() {
        Member member1 = new Member("TestMember1");
        member1.setId(1L);
        Member member2 = new Member("TestMember2");
        member2.setId(2L);

        repository.save(member1);
        repository.save(member2);

        Optional<Member> optionalMember1 = repository.findByName("TestMember1");
        Member getMember1 = optionalMember1.get();
        Assertions.assertThat(getMember1).isInstanceOf(Member.class);

        Optional<Member> optionalMember2 = repository.findByName("TestMember2");
        Member getMember2 = optionalMember2.get();
        Assertions.assertThat(getMember2).isInstanceOf(Member.class);

        Optional<Member> optionalMember3 = repository.findByName("NullName");
        assertTrue(optionalMember3.isEmpty());


    }

    @Test
    @DisplayName("find All")
    public void findAll() {
        Member member1 = new Member("TestMember1");
        member1.setId(1L);
        Member member2 = new Member("TestMember2");
        member2.setId(2L);
        Member member3 = new Member("TestMember3");
        member3.setId(3L);

        repository.save(member1);
        repository.save(member2);
        repository.save(member3);

        ArrayList<Member> memberArrayList = repository.findAll();
        Assertions.assertThat(memberArrayList.size()).isEqualTo(3);

    }


}