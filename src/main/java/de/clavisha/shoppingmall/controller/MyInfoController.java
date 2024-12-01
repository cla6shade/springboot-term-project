package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Coupon;
import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.form.MyInfoUpdateForm;
import de.clavisha.shoppingmall.repository.CouponRepository;
import de.clavisha.shoppingmall.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/myInfo")
public class MyInfoController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponRepository couponRepository;
    @GetMapping
    public String myProfilePage(Model model) {
        Member member = getCurrentMember();
        List<Coupon> coupons = couponRepository.findAllByMember(member);
        model.addAttribute("coupons", coupons);
        model.addAttribute("member", member);
        return "pages/myInfo/index";
    }
    @PostMapping
    public String changeProfile(@Valid @ModelAttribute MyInfoUpdateForm form, Model model){
        Member updatedMember = memberService.updateMember(form, getCurrentMember());
        model.addAttribute("message", "정보가 성공적으로 업데이트되었습니다.");
        model.addAttribute("member", updatedMember);
        return "pages/myInfo/index";
    }
    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return authentication != null ? authentication.getName() : "Anonymous";
    }
    private Member getCurrentMember() {
        return memberService.getMemberByLoginId(getCurrentUserName());
    }
}
