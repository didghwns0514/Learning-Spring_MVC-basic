package hello.hellospring.controller;


import hello.hellospring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller // @Bean @Component 마냥, Spring이 넣어두고 관리하게 되는 대상으로 처음 시작때 등록
@RequiredArgsConstructor // private 붙은 것에 생성자 주입 자동으로 관리
public class MemberController {

    private final MemberService memberService;

/* 밑에와 같이 생성자 주입 사용하여도 됨*/
//    @Autowired // 한개짜리면 없어도 자동으로 연결
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }



}
