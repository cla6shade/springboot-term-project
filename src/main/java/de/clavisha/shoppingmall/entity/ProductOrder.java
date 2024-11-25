package de.clavisha.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Short orderCount;

    @Column(nullable = false)
    private Boolean paymentStatus;

    @Column(nullable = false)
    private Boolean deliveryStatus;
}
