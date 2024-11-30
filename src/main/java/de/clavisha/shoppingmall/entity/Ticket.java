package de.clavisha.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Ticket(Product product, String title, String content, Member member) {
        this.product = product;
        this.title = title;
        this.content = content;
        this.member = member;
    }
}
