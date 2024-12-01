package de.clavisha.shoppingmall.entity;

import de.clavisha.shoppingmall.enumerates.DeliveryStatus;
import de.clavisha.shoppingmall.enumerates.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private String address;

    @Column(nullable = false)
    private Short orderCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Builder
    public ProductOrder(Product product, Member member, Short orderCount) {
        this.product = product;
        this.member = member;
        this.orderCount = orderCount;
        this.paymentStatus = PaymentStatus.INCOMPLETE;
        this.deliveryStatus = DeliveryStatus.PREPARING;
    }
}
