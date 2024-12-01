package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.entity.Product;
import de.clavisha.shoppingmall.entity.Ticket;
import de.clavisha.shoppingmall.entity.TicketReply;
import de.clavisha.shoppingmall.form.TicketForm;
import de.clavisha.shoppingmall.repository.ProductRepository;
import de.clavisha.shoppingmall.repository.TicketReplyRepository;
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
    @Autowired
    private TicketReplyRepository ticketReplyRepository;

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

    @GetMapping("/edit/{ticketId}")
    public String editTicketPage(@PathVariable("ticketId") Long ticketId,
                                 Model model) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        model.addAttribute("ticket", ticket);
        return "pages/tickets/ticketEdit";
    }

    @PostMapping("/edit/{ticketId}")
    public String editTicket(@PathVariable("ticketId") Long ticketId,
                                 TicketForm form,
                                 RedirectAttributes redirectAttributes) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setContent(form.getContent());
        ticket.setTitle(form.getTitle());
        ticketRepository.save(ticket);
        redirectAttributes.addFlashAttribute("message", "문의사항 수정이 완료되었습니다.");
        return "redirect:/tickets/view/" + ticketId;
    }

    @PostMapping("/reply/{ticketId}")
    public String replyTicket(@PathVariable("ticketId") Long ticketId,
                              TicketForm form,
                              RedirectAttributes redirectAttributes) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        TicketReply reply = TicketReply.builder()
                .ticket(ticket)
                .content(form.getContent())
                .member(getCurrentMember())
                .build();
        ticketReplyRepository.save(reply);
        return "redirect:/tickets/view/" + ticketId;
    }


    @GetMapping("/view/{ticketId}")
    public String ticketViewPage(@PathVariable("ticketId") Long ticketId, Model model) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        model.addAttribute("ticket", ticket);
        List<TicketReply> replies = ticketReplyRepository.findAllByTicketId(ticketId);
        model.addAttribute("replies", replies);
        return "pages/tickets/ticketInfo";
    }

    @GetMapping("/delete")
    public String deleteTicket(@RequestParam("ticketId") Long ticketId, RedirectAttributes redirectAttributes) {
        ticketRepository.deleteById(ticketId);
        redirectAttributes.addFlashAttribute("message", "문의 삭제가 완료되었습니다.");
        return "redirect:/tickets/myTickets";
    }

    @GetMapping("/myTickets")
    public String myTicketsPage(Model model) {
        List<Ticket> tickets = ticketRepository.findAllByMember(getCurrentMember());
        model.addAttribute("tickets", tickets);
        return "pages/tickets/myTickets";
    }
    @GetMapping("/new")
    public String newTicketsPage(Model model) {
        model.addAttribute("product", null);
        return "pages/tickets/index";
    }

    @GetMapping("/new/{productId}")
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
