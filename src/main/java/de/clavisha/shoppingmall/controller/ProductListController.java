package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Cart;
import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.entity.Wishlist;
import de.clavisha.shoppingmall.repository.CartRepository;
import de.clavisha.shoppingmall.repository.ProductRepository;
import de.clavisha.shoppingmall.repository.WishlistRepository;
import de.clavisha.shoppingmall.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/myProducts")
public class ProductListController {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping
    public String myProductsPage(Model model) {
        Member member = getCurrentMember();
        List<Cart> cart = cartRepository.findAllByMemberId(member.getId());
        List<Wishlist> wishlist = wishlistRepository.findAllByMemberId(member.getId());
        model.addAttribute("cart", cart);
        model.addAttribute("wishlist", wishlist);
        return "pages/product/myProducts";
    }
    @GetMapping("/cart/remove/{id}")
    public String deleteFromCart(@PathVariable("id") Long cartId, RedirectAttributes redirectAttributes) {
        cartRepository.deleteById(cartId);
        redirectAttributes.addFlashAttribute("message", "장바구니에서 상품을 제거했습니다.");
        return "redirect:/myProducts";
    }
    @GetMapping("/wishlist/remove/{id}")
    public String deleteFromWishlist(@PathVariable("id") Long wishlistId, RedirectAttributes redirectAttributes) {
        wishlistRepository.deleteById(wishlistId);
        redirectAttributes.addFlashAttribute("message", "위시리스트에서 상품을 제거했습니다.");
        return "redirect:/myProducts";
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
