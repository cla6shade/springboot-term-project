package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.form.RegisterForm;
import de.clavisha.shoppingmall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
@Controller
public class AuthController {
    @Autowired
    private MemberService memberService;
    @PostMapping("/auth/register")
    public String registerUser(@Valid @ModelAttribute RegisterForm registerForm,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "pages/auth/register";
        }

        try {
            memberService.registerMember(registerForm); // 서비스에서 회원 저장 로직 처리
            model.addAttribute("successMessage", "로그인에 성공했습니다. 가입에 사용한 id와 비밀번호로 로그인해주세요.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "회원가입 처리 중 에러가 발생했습니다.");
            return "pages/auth/register";
        }
    }
}
