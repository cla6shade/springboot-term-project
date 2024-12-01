package de.clavisha.shoppingmall.entity;

import de.clavisha.shoppingmall.enumerates.CouponType;
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
    private Member member;

    private Integer discountAmount;

    private Boolean isUsed;

    private Boolean isProportional;
}
