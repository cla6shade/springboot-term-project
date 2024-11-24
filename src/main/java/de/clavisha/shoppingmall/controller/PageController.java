package de.clavisha.shoppingmall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/")
public class PageController {
    @GetMapping
    public String mainPage() {
        return "pages/index";
    }

    @GetMapping("login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "가입하지 않은 사용자이거나 비밀번호가 올바르지 않습니다.");
        }
        return "pages/auth/login";
    }

    @GetMapping("register")
    public String registerPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "가입하지 않은 사용자이거나 비밀번호가 올바르지 않습니다.");
        }
        return "pages/auth/register";
    }
}
