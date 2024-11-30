package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Product;
import de.clavisha.shoppingmall.entity.ProductReview;
import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.entity.ProductSubReview;
import de.clavisha.shoppingmall.form.ProductReviewForm;
import de.clavisha.shoppingmall.form.ProductSubReviewForm;
import de.clavisha.shoppingmall.repository.ProductRepository;
import de.clavisha.shoppingmall.repository.ProductReviewRepository;
import de.clavisha.shoppingmall.repository.MemberRepository;
import de.clavisha.shoppingmall.repository.ProductSubReviewRepository;
import de.clavisha.shoppingmall.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/reviews")
public class ProductReviewController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductReviewRepository productReviewRepository;


    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductSubReviewRepository productSubReviewRepository;

    // 리뷰 작성 처리
    @PostMapping("/add")
    public String addReview(@Valid @ModelAttribute ProductReviewForm productReviewForm,
                            @RequestParam Long productId,
                            RedirectAttributes redirectAttributes) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        Member member = getCurrentMember();

        ProductReview review = ProductReview.builder()
                .member(member)
                .stars(productReviewForm.getStars())
                .title(productReviewForm.getTitle())
                .product(product)
                .content(productReviewForm.getContent()).build();
        redirectAttributes.addFlashAttribute("review", review);

        productReviewRepository.save(review);

        return "redirect:/products/" + productId;
    }
    @PostMapping("/subreviews/add")
    public String addSubReview(@Valid @ModelAttribute ProductSubReviewForm subReviewForm) {
        ProductReview review = productReviewRepository.findById(subReviewForm.getParentReviewId())
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        Member member = getCurrentMember();

        ProductSubReview subReview = ProductSubReview.builder()
                .parentReview(review)
                .member(member)
                .content(subReviewForm.getContent())
                .product(review.getProduct())
                .build();

        productSubReviewRepository.save(subReview);

        return "redirect:/products/" + review.getProduct().getId();
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
