package de.clavisha.shoppingmall.repository;

import de.clavisha.shoppingmall.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAllByMemberId(Long memberId);
}