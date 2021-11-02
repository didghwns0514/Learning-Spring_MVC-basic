package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findByID(Long id);
    Optional<Member> findByName(String name);

    ArrayList<Member> findAll();

}
