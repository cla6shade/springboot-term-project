package de.clavisha.shoppingmall.repository;

import de.clavisha.shoppingmall.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    public List<ProductOrder> findAllByMemberId(Long memberId);
    public List<ProductOrder> findAllByMemberIdAndProductId(Long memberId, Long productId);
}
