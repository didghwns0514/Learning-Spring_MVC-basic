package hello.hellospring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity // JPA가 관리한다!
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 값의 관리, 채우는 값을 DB가 알아서 생성해주는 것이 Identity 전략
    private Long id;
    //@Column(name = "NAME") -> 만약 column 이름이 name이 아닌 경우, 옆에처럼 명시해주면 됨
    private String name;

    public Member() {
    }

    public Member(String name){
        this.name = name;
    }


}
