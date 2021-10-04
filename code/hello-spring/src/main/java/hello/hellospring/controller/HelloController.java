package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // -> /hello로 들어오면 아래 메서드 실행
    public String Hello(Model model) {
        // model, view, controller의 model이 이부분
        model.addAttribute("data", "hello");
        return "hello";

    }
}
