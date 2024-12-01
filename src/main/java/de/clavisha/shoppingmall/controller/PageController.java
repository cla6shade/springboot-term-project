package de.clavisha.shoppingmall.controller;

import de.clavisha.shoppingmall.entity.Category;
import de.clavisha.shoppingmall.entity.Product;
import de.clavisha.shoppingmall.repository.CategoryRepository;
import de.clavisha.shoppingmall.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
public class PageController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;
    @GetMapping
    public String mainPage(@RequestParam(value = "category", required = false) Long categoryId, Model model) {
        List<Category> categories = categoryRepository.findAll();
        categories.forEach(category -> category.setSelected(category.getId().equals(categoryId)));
        List<Product> products;

        if (categoryId != null) {
            products = productService.getProductsByCategoryId(categoryId);
        } else {
            products = productService.getAllProducts();
        }
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "pages/index";
    }

    @GetMapping("find")
    public String findPage() {
        return "pages/auth/find";
    }

    @GetMapping("login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "가입하지 않은 사용자이거나 비밀번호가 올바르지 않습니다.");
        }
        return "pages/auth/login";
    }

    @GetMapping("register")
    public String registerPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "가입하지 않은 사용자이거나 비밀번호가 올바르지 않습니다.");
        }
        return "pages/auth/register";
    }

}
