package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository // 이부분 추가 -> Controller / Service / Repository 정형화된 패턴 사용
public class MemoryMemberRepository implements MemberRepository{
    /**
     * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
     */
    private Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) { //
        member.setId(++sequence); // 1 올리고 할당
        store.put(member.getId(), member); // By reference
        return member;
    }

    @Override
    public Optional<Member> findByID(Long id) { // Optional로 null 대비
        return Optional.ofNullable(this.store.get(id)); // Null인경우 클라이언트(가져다쓰는 부분)에서 후처리 가능
    }

    @Override
    public Optional<Member> findByName(String name) {
        return this.store.values().stream()
                .filter(member -> member.getName().equals(name)) // String 메서드
                .findAny(); // 어떤 값이던 1개 가져오고 없으면 Null
    }

    @Override
    public ArrayList<Member> findAll() {
        return new ArrayList<Member>(store.values());
    }

    public static long getSequence() {
        return sequence;
    }

    public void storeClear() {
        store.clear();
    }
}
