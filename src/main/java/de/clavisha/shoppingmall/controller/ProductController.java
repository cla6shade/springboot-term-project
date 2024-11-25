package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Category;
import de.clavisha.shoppingmall.entity.Product;
import de.clavisha.shoppingmall.entity.ProductReview;
import de.clavisha.shoppingmall.repository.CategoryRepository;
import de.clavisha.shoppingmall.service.ProductService;
import de.clavisha.shoppingmall.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ReviewService reviewService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/{id}")
    public String productDetails(@PathVariable("id") Long productId, Model model) {
        Product product = productService.getProductById(productId);
        List<ProductReview> reviews = reviewService.getReviewsByProductId(productId);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("product", product);
        model.addAttribute("reviews", reviews);
        model.addAttribute("categories", categories);
        return "pages/product/index";
    }
}
