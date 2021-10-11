package hello.hellospring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class Member {


    private String name;
    private Long id;

    public Member(String name){
        this.name = name;
    }


}
