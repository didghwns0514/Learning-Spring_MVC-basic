package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/*
* 스프링에게 직접 전달받아서 테스트 구성하는 방법
* */
@SpringBootTest
@Transactional
@RequiredArgsConstructor
class MemberServiceIntegrationTest {

    // 변수 주입 -> JUnit 특성 때문에 rombok으로는 주입할 수 없음(JUnit이 DI 먼저 개입)
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


//    @SpringBootTest 를 통해 주입을 스프링에서 하도록 세팅을 하기 때문에, 직접 객체 생성하여 주입하지 않아도 됨
//    @BeforeEach
//    void beforeEach() {
//        memoryMemberRepository = new MemoryMemberRepository();
//        memberService = new MemberService(memoryMemberRepository); // 분리해서 clear 가능하도록 구현
//    }

//    @Transactional로 인하여, 자동으로 release되도록 설정
//    @AfterEach
//    void afterEach() {
//        memoryMemberRepository.storeClear(); // 테스트환경 보장
//    }


    @Test
    @DisplayName("정상상태 Join")
    void join1() {
        //given
        Member member1 = new Member("Member 1");

        //when
        Long returnID = memberService.join(member1);

        //then
        memberService.findOne(returnID).map(Member::getId)
                .ifPresent(aLong -> Assertions.assertEquals(aLong, returnID));
        memberService.findOne(returnID).map(Member::getName)
                .ifPresent(nString -> Assertions.assertEquals(nString, member1.getName()));

    }

    @Test
    @DisplayName("오류상태 Join - 중복회원 이름 검출 검증")
    void join2() {

        // given
        Member memberD1 = new Member("Dummy1");
        Member memberD2 = new Member("Dummy1");

        // when
        memberService.join(memberD1);


        // then
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
            memberService.join(memberD2);
        });
        org.assertj.core.api.Assertions.assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 회원 이름입니다.");

    }

}