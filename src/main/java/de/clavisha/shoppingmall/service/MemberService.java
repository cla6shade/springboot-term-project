package de.clavisha.shoppingmall.service;

import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.form.MyInfoUpdateForm;
import de.clavisha.shoppingmall.form.RegisterForm;
import de.clavisha.shoppingmall.repository.MemberRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void registerMember(RegisterForm registerForm) {
        if (memberRepository.findByLoginId(registerForm.getLoginId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 로그인 id입니다.");
        }
        String encodedPassword = passwordEncoder.encode(registerForm.getPassword());

        Member newMember = Member.builder()
                .loginId(registerForm.getLoginId())
                .password(encodedPassword)
                .contact(registerForm.getContact())
                .name(registerForm.getName())
                .build();

        memberRepository.save(newMember);
    }
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }

    public void useSavings(int savingsUsageAmount, Member member) {
        int savings = member.getSavings() - savingsUsageAmount;
        if(savings < 0) {
            throw new RuntimeException("Shortage of member savings");
        }
        member.setSavings(savings);
        memberRepository.save(member);
    }

    public Member getMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }
    public Member updateMember(@Valid @ModelAttribute MyInfoUpdateForm form, Member member) {
        member.setName(form.getName());
        member.setContact(form.getContact());
        return memberRepository.save(member);
    }
    public void deleteUser(String loginId) {
        Member member = getMemberByLoginId(loginId);
        memberRepository.delete(member);
    }
}
