package de.clavisha.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String loginId;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 32)
    private String contact;

    private Integer savings;

    @Builder
    public Member(String loginId, String password, String name, String contact) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.savings = 0;
    }
}