package hello.hellospring.controller;

public class MemberForm {

    private String name; // create Members에 Form의 Name과 매칭이 됨

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
