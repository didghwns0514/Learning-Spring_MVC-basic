package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // domain 완전 처음 -> template작성하면 그것 불러옴
    public String home() {
        return "home";
    }


}
