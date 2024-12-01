package de.clavisha.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class TicketReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="ticket_id", nullable=false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name="member_id", nullable=false)
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public TicketReply(Ticket ticket, Member member, String content) {
        this.ticket = ticket;
        this.content = content;
        this.member = member;
    }
}
