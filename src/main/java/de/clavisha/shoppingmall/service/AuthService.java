package de.clavisha.shoppingmall.service;

import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found with loginId: " + username);
        }
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }

}
