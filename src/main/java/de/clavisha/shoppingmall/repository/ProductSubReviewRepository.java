package de.clavisha.shoppingmall.repository;

import de.clavisha.shoppingmall.entity.ProductSubReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductSubReviewRepository extends JpaRepository<ProductSubReview, Long> {
    List<ProductSubReview> findAllByParentReviewId(Long parentReviewId);
}
