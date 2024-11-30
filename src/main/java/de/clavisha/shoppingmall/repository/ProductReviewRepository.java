package de.clavisha.shoppingmall.repository;

import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.entity.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProductId(Long productId);
    List<ProductReview> findAllByMemberAndProductId(Member member, Long productId);
}