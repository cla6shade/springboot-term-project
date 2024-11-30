package de.clavisha.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ProductSubReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private ProductReview parentReview;

    @Builder
    public ProductSubReview(Member member, Product product, String content, ProductReview parentReview) {
        this.member = member;
        this.product = product;
        this.content = content;
        this.parentReview = parentReview;
    }
}
