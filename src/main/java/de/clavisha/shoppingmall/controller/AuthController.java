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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private MemberService memberService;
    @PostMapping("/auth/register")
    public String registerUser(@Valid @ModelAttribute RegisterForm registerForm,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", true);
            redirectAttributes.addFlashAttribute("message", "잘못된 입력값입니다.");
            return "redirect:/register";
        }

        try {
            memberService.registerMember(registerForm);
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", true);
            redirectAttributes.addFlashAttribute("message", "회원가입 처리 중 에러가 발생했습니다.");
            return "redirect:/register?error=true";
        }
    }
}
