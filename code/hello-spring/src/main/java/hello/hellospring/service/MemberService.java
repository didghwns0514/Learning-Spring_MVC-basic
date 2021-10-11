package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberService {
    /**
     * > Service layer에서 "어떻게 처리할지"에 대한 세부 내용이 들어가주어야 함
     * - 역할과 구현은 Spring DI로 구현하고 이 역할을 가져다 쓰는 서비스 부분에서 처리를 추가로 해줌
     *
     * > Service layer의 naming은 좀더 비지니스에 가까운 naming을 사용
     * > Service는 비지니스에 더 의존적으로 설계하고 리포지토리는 개발자스럽게 구현
     * - Service : Join, FindMembers
     * - Repository : save, findAll
     */
    private final MemberRepository memberRepository;

    // 회원가입, 중복 이름 제거(user_id라고 가정)
    public Long join(Member member) {

        validateDuplicateNameMember(member); // 중복회원 검증, 함수는 하나의 일만 하도록 하기 위해 쪼개버림

        memberRepository.save(member);

        return member.getId();

    }

    private void validateDuplicateNameMember(Member member) {
        // 중복이름이 있는지 검사
        memberRepository.findByName(member.getName())
                // isPresent 로 필요한 부분 검색해서 원하는 동작 구현 가능
                    .ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원 이름입니다.");
                });
    }

    /**
     * Role : 전체 회원 조회
     */
    public ArrayList<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * Role : ID로 조회하기
     */
    public Optional<Member> findOne(Long memberID) {
        return memberRepository.findByID(memberID);
    }


}
