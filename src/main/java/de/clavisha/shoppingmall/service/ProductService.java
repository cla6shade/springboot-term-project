package de.clavisha.shoppingmall.service;

import de.clavisha.shoppingmall.entity.Product;
import de.clavisha.shoppingmall.entity.ProductOrder;
import de.clavisha.shoppingmall.repository.ProductOrderRepository;
import de.clavisha.shoppingmall.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderRepository productOrderRepository;

    public List<ProductOrder> getOrdersByMemberId(Long memberId) {
        return productOrderRepository.findAllByMemberId(memberId);
    }

    public ProductOrder getOrderById(Long orderId) {
        return productOrderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderId));
    }

    public void removeOrder(ProductOrder order) {
        productOrderRepository.delete(order);
    }

    public void issueOrder(ProductOrder productOrder) {
        if(!canBuyProduct(productOrder.getProduct(), productOrder.getOrderCount())) {
            throw new RuntimeException("주문 수량이 주문 가능한 수량보다 많습니다.");
        }
        Product product = productOrder.getProduct();
        product.setStock(product.getStock() - productOrder.getOrderCount());
        saveProduct(product);
        // 쿠폰 사용 및 포인트 차감
        saveOrder(productOrder);
    }

    public void saveOrder(ProductOrder productOrder) {
        productOrderRepository.save(productOrder);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public boolean hasOrderedBefore(Long memberId, Long productId) {
        List<ProductOrder> orders = productOrderRepository.findAllByMemberIdAndProductId(memberId, productId);
        return !orders.isEmpty();
    }

    public boolean canBuyProduct(Product product, Short amount) {
        return product.getStock() >= amount;
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }
}
