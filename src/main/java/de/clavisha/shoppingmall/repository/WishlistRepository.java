package de.clavisha.shoppingmall.repository;

import de.clavisha.shoppingmall.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findAllByMemberId(Long memberId);
}
