package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.entity.Product;
import de.clavisha.shoppingmall.entity.Ticket;
import de.clavisha.shoppingmall.form.TicketForm;
import de.clavisha.shoppingmall.repository.ProductRepository;
import de.clavisha.shoppingmall.repository.TicketRepository;
import de.clavisha.shoppingmall.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping
    public String createTicket(@ModelAttribute @Valid TicketForm ticketForm, RedirectAttributes redirectAttributes) {
        Ticket ticket = Ticket.builder()
                .member(getCurrentMember())
                .title(ticketForm.getTitle())
                .content(ticketForm.getContent())
                .build();

        if (ticketForm.getProductId() != null) {
            ticket.setProduct(productRepository.findById(ticketForm.getProductId()).orElseThrow());
        }

        ticketRepository.save(ticket);

        return "redirect:/tickets/myTickets";
    }

    @GetMapping("/myTickets")
    public String myTicketsPage(Model model) {
        List<Ticket> tickets = ticketRepository.findAllByMember(getCurrentMember());
        model.addAttribute("tickets", tickets);
        return "pages/tickets/myTickets";
    }
    @GetMapping
    public String newTicketsPage(Model model) {
        model.addAttribute("product", null);
        return "pages/tickets/index";
    }

    @GetMapping("/{productId}")
    public String newTicketsPageWithProduct(@PathVariable Long productId, Model model) {
        Product product = productRepository.findById(productId).orElse(null);
        model.addAttribute("product", product);
        return "pages/tickets/index";
    }
    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return authentication != null ? authentication.getName() : "Anonymous";
    }
    private Member getCurrentMember() {
        return memberService.getMemberByLoginId(getCurrentUserName());
    }

}
