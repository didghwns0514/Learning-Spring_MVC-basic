package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") // -> /hello로 들어오면 아래 메서드 실행
    public String Hello(Model model) {
        // model, view, controller의 model이 이부분
        model.addAttribute("data", "hello");
        return "hello";

    }

    @GetMapping("hello-mvc")
    public String HelloMVC(@RequestParam(name = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping(value = "hello-string")
    @ResponseBody // 응답 Body 부분에 직접 response 를 넣어서 돌려준다는 의미
    public String helloString(@RequestParam(name = "name", required = false) String name) {
        return "Hello! This is response from String : " + name; // 이 문자가 그대로 나감
    }

    @GetMapping(value = "hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam(name="name", required = false) String name) {
        return new Hello(name); // 객체가 넘어가게 됨

    }

    static class Hello{ // Static으로 선언하면 load되기 전에 Outer Class에서 먼저 사용할 수 있음

        private String name; // 이부분이 JSON의 값으로 나타남 -> {"name":null}

        public Hello(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
