package de.clavisha.shoppingmall.repository;

import de.clavisha.shoppingmall.entity.TicketReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketReplyRepository extends JpaRepository<TicketReply, Long> {
    public List<TicketReply> findAllByTicketId(Long ticketId);
}
