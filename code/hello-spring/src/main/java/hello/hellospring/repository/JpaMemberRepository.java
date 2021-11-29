package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // JPA에서는 모든 DB 정보를 옆의 객체로 주입받음(SpringBoot가 알아서 관리하게 됨)

//    public JpaMemberRepository(EntityManager em) {
//        this.em = em;
//    }

    @Override
    public Member save(Member member) {
        em.persist(member); // JPA ; Insert 수행
        return member;
    }

    @Override
    public Optional<Member> findByID(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> memberList = em.createQuery("select m from Member m where m.name= :name", Member.class)
                .setParameter("name",name)
                .getResultList();
        return memberList.stream().findAny();
    }

    @Override
    public ArrayList<Member> findAll() {
        // JPQL : 테이블 대상으로 query를 날리는 것이 아닌, 객체 대상의 JAP sql 유사 문법
        return (ArrayList<Member>) em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
