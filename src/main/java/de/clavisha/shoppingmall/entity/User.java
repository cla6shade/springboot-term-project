package de.clavisha.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String loginId;

    @Column(nullable = false, length = 88)
    private String password;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 32)
    private String contact;

    private Integer savings;
}