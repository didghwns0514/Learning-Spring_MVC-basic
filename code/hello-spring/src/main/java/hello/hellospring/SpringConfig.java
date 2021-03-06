package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
//@RequiredArgsConstructor
public class SpringConfig {

    // private final DataSource dataSource; // 데이터소스 Spring에서 생성 이후, Autowired로 주입
    // Autowired로 주입할 수 있는 이유는, @Configuration 자체도 스프링에서 관리하는 것이므로
    // Cascading 형태로 관리를 내려가면서 해주는 형식이기 때문에 가능한듯

//    private final EntityManager em;

    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //    @Autowired
//    public SpringConfig(DataSource dataSource, EntityManager em) {
//
//        //this.dataSource = dataSource;
//        this.em = em;
//    }

    @Bean
    public MemberService memberService() {
//        return new MemberService(memberRepository());
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository() {
//
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSource);
////        return new JdbcTemplateMemberRepository(dataSource);
////        return new JpaMemberRepository(em);
////        return new SpringDataJpaMemberRepository();
//    }

}
