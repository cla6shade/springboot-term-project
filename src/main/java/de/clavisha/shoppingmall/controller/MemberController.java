package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // 모든 요청이 /users로 시작됨
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResponseEntity<Member> getUserInfo(@AuthenticationPrincipal UserDetails currentUser) {
        Member member = memberService.getMemberByLoginId(currentUser.getUsername());
        return ResponseEntity.ok(member);
    }

    @PutMapping
    public ResponseEntity<Member> updateUser(@AuthenticationPrincipal UserDetails currentUser, @RequestBody Member updatedMemberDetails) {
        Member updatedMember = memberService.updateUser(currentUser.getUsername(), updatedMemberDetails);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails currentUser) {
        memberService.deleteUser(currentUser.getUsername());
        return ResponseEntity.noContent().build();
    }
}
