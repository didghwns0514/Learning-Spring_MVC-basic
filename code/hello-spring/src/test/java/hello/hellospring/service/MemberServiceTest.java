package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memoryMemberRepository;

    @BeforeEach
    void beforeEach() {
        memoryMemberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memoryMemberRepository); // 분리해서 clear 가능하도록 구현
    }

    @AfterEach
    void afterEach() {
        memoryMemberRepository.storeClear(); // 테스트환경 보장
    }

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
        Member memberD3 = new Member("Dummy2");

        // when
        memberService.join(memberD1);


        // then
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
            memberService.join(memberD2);
        });
        org.assertj.core.api.Assertions.assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 회원 이름입니다.");


        // try-catch로 Exception handling
        try {
            // should not reach here
            memberService.join(memberD2);
            fail("fail!");
        } catch (IllegalStateException e) {
            org.assertj.core.api.Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 이름입니다.");
        }

    }

}