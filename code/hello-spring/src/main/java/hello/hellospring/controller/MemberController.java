package hello.hellospring.controller;


import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller // @Bean @Component 마냥, Spring이 넣어두고 관리하게 되는 대상으로 처음 시작때 등록
@RequiredArgsConstructor // private 붙은 것에 생성자 주입 자동으로 관리
public class MemberController {

    private final MemberService memberService;

/* 밑에와 같이 생성자 주입 사용하여도 됨*/
//    @Autowired // 한개짜리면 없어도 자동으로 연결
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMembersForm";
    }

    @GetMapping("/members")
    public String membersList(Model model) {

        ArrayList<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/searchMembersList";

    }

    @PostMapping("members/new") // Get으로 보여주고 나서 Form 받을 때 사용 Post 주의
    public String create(MemberForm form) { // MemberForm으로 front 에서 form다 받아옴

        Member member = new Member(form.getName()); // getName으로 설정
        memberService.join(member); // 회원 등록 서비스 호출 -> exception 상황 있음

        System.out.println("Current memberService Status = \n" + memberService);

        return "redirect:/"; // Home 화면으로 다시 redirect 해줌

    }


}
