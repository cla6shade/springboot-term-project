package de.clavisha.shoppingmall.entity;

import de.clavisha.shoppingmall.enumerates.DeliveryStatus;
import de.clavisha.shoppingmall.enumerates.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Builder
    public ProductOrder(Product product, Member member, String address, Short orderCount) {
        this.product = product;
        this.member = member;
        this.address = address;
        this.orderCount = orderCount;
        this.paymentStatus = PaymentStatus.COMPLETE;
        this.deliveryStatus = DeliveryStatus.PREPARING;
    }
}
