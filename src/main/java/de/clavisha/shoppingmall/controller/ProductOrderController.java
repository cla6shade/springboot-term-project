package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Coupon;
import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.entity.Product;
import de.clavisha.shoppingmall.entity.ProductOrder;
import de.clavisha.shoppingmall.enumerates.DeliveryStatus;
import de.clavisha.shoppingmall.enumerates.PaymentStatus;
import de.clavisha.shoppingmall.form.ProductOrderForm;
import de.clavisha.shoppingmall.form.ProductOrderPaymentForm;
import de.clavisha.shoppingmall.repository.CouponRepository;
import de.clavisha.shoppingmall.service.MemberService;
import de.clavisha.shoppingmall.service.ProductService;
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
@RequestMapping("/order")
public class ProductOrderController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CouponRepository couponRepository;
    @GetMapping("/refund/{id}")
    public String refundProduct(@PathVariable("id") Long orderId,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.refundProduct(orderId);
            redirectAttributes.addFlashAttribute("message", "환불이 완료되었습니다.");
            return "redirect:/order";
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("message", "환불 처리에 실패했습니다.");
            return "redirect:/order";
        }
    }
    @GetMapping("/return/{id}")
    public String returnProduct(@PathVariable("id") Long orderId,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.returnProduct(orderId);
            redirectAttributes.addFlashAttribute("message", "반품 처리가 완료되었습니다. 반품은 약 3일이 소요될 수 있습니다.");
            return "redirect:/order";
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("message", "반품 처리에 실패했습니다.");
            return "redirect:/order";
        }
    }
    @GetMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable("id") Long orderId,
                              RedirectAttributes redirectAttributes) {
        try {
            productService.cancelOrder(orderId);
            redirectAttributes.addFlashAttribute("message", "주문이 성공적으로 취소되었습니다.");
            return "redirect:/order";
        } catch(Exception e) {
            redirectAttributes.addFlashAttribute("message", "주문 취소에 실패했습니다.");
            return "redirect:/order";
        }
    }
    @GetMapping("/pay/{id}")
    public String paymentPage(@PathVariable("id") Long orderId, Model model) {
        ProductOrder order = productService.getOrderById(orderId);
        Product product = order.getProduct();
        if(order.getPaymentStatus() != PaymentStatus.INCOMPLETE) {
            return "redirect:/order";
        }
        int price = order.getOrderCount() * product.getPrice();
        List<Coupon> coupons = couponRepository.findAllByMember(getCurrentMember());
        int savings = getCurrentMember().getSavings();
        model.addAttribute("order", order);
        model.addAttribute("totalPrice", price);
        model.addAttribute("coupons", coupons);
        model.addAttribute("savings", savings);
        return "pages/order/payment";
    }
    @PostMapping("/pay")
    public String makePayment(@Valid @ModelAttribute ProductOrderPaymentForm form,
                              @RequestParam("orderId") Long orderId) {
        productService.makePayment(form, orderId);
        int savings = form.getSavingsUsageAmount();
        memberService.useSavings(savings, getCurrentMember());
        return "pages/order/success";
    }
    @PostMapping
    public String orderProduct(@Valid @ModelAttribute ProductOrderForm form,
                               @RequestParam("productId") Long productId){
        ProductOrder savedOrder = productService.issueOrder(form, productId, getCurrentMember());
        return "redirect:/order/pay/" + savedOrder.getId();
    }
    @GetMapping
    public String myOrdersPage(Model model) {
        Member member = getCurrentMember();
        List<ProductOrder> orders = productService.getOrdersByMemberId(member.getId());
        model.addAttribute("orders", orders);
        return "pages/order/myOrders";
    }
    @GetMapping("/{id}")
    public String orderPage(@PathVariable("id") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        model.addAttribute("product", product);
        return "pages/order/index";
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
