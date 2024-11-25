package de.clavisha.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member user;

    private Byte discountPercent;

    private Integer discountPrice;

    private Byte type;
}
