package de.clavisha.shoppingmall.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@Controller
@RequestMapping("/")
public class PageController {
    @GetMapping
    public String mainPage(Model model) {
        boolean isLoggedIn = checkUserLoginStatus(); // 로그인 상태 확인
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("username", isLoggedIn ? getLoggedInUsername() : null);
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


    private boolean checkUserLoginStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));
    }

    private String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : null;
    }

}
