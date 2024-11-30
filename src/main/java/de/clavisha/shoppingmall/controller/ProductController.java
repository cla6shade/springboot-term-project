package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.*;
import de.clavisha.shoppingmall.repository.CategoryRepository;
import de.clavisha.shoppingmall.repository.ProductReviewRepository;
import de.clavisha.shoppingmall.repository.ProductSubReviewRepository;
import de.clavisha.shoppingmall.service.MemberService;
import de.clavisha.shoppingmall.service.ProductService;
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

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private ProductSubReviewRepository productSubReviewRepository;

    @GetMapping("/{id}")
    public String productDetails(@PathVariable("id") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        List<ProductReview> reviews = productReviewRepository.findByProductId(productId);
        List<Category> categories = categoryRepository.findAll();
        List<ProductReview> reviewsBefore = productReviewRepository.findAllByMemberAndProductId(getCurrentMember(), productId);
        List<ProductSubReview> subReviews = productSubReviewRepository.findAllByParentReviewId(productId);
        boolean hasOrderedBefore = productService.hasOrderedBefore(getCurrentMember().getId(), productId);
        boolean canWriteReview = hasOrderedBefore && reviewsBefore.isEmpty();
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("categories", categories);
        model.addAttribute("hasOrderedBefore", hasOrderedBefore);
        model.addAttribute("subReviews", subReviews);
        model.addAttribute("canWriteReview", canWriteReview);
        return "pages/product/index";
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
