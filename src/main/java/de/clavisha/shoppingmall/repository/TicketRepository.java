package de.clavisha.shoppingmall.repository;

import de.clavisha.shoppingmall.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByProductId(Long productId);
    List<Ticket> findAllByMemberId(Long memberId);
}
