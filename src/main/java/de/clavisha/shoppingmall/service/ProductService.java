package de.clavisha.shoppingmall.service;

import de.clavisha.shoppingmall.entity.Coupon;
import de.clavisha.shoppingmall.entity.Member;
import de.clavisha.shoppingmall.entity.Product;
import de.clavisha.shoppingmall.entity.ProductOrder;
import de.clavisha.shoppingmall.enumerates.DeliveryStatus;
import de.clavisha.shoppingmall.enumerates.PaymentStatus;
import de.clavisha.shoppingmall.form.ProductOrderForm;
import de.clavisha.shoppingmall.form.ProductOrderPaymentForm;
import de.clavisha.shoppingmall.repository.CouponRepository;
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
    
    @Autowired
    private CouponRepository couponRepository;

    public List<ProductOrder> getOrdersByMemberId(Long memberId) {
        return productOrderRepository.findAllByMemberId(memberId);
    }

    public ProductOrder getOrderById(Long orderId) {
        return productOrderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("주문내역을 찾을 수 없습니다: " + orderId));
    }

    public void refundProduct(Long orderId) {
        ProductOrder order = getOrderById(orderId);        
        if(order.getPaymentStatus() == PaymentStatus.COMPLETE && order.getDeliveryStatus() == DeliveryStatus.PREPARING) {
            order.setPaymentStatus(PaymentStatus.REFUNDED);
            saveOrder(order);
            return;
        }
        throw new RuntimeException("환불 가능한 상태가 아닙니다.");
    }


    public void returnProduct(Long orderId) {
        ProductOrder order = getOrderById(orderId);
        if(order.getPaymentStatus() == PaymentStatus.COMPLETE && order.getDeliveryStatus() == DeliveryStatus.DELIVER_COMPLETED) {
            order.setPaymentStatus(PaymentStatus.REFUNDED);
            order.setDeliveryStatus(DeliveryStatus.RETURNED);
            saveOrder(order);
            return;
        }
        throw new RuntimeException("반품 가능한 상태가 아닙니다.");
    }

    public void cancelOrder(Long orderId) {
        ProductOrder order = getOrderById(orderId);
        if(order.getPaymentStatus() == PaymentStatus.INCOMPLETE) {
            productOrderRepository.delete(order);
            return;
        }
        throw new RuntimeException("결제되지 않은 주문입니다.");
    }

    public void makePayment(ProductOrderPaymentForm form, Long orderId) {
        ProductOrder order = getOrderById(orderId);
        order.setAddress(form.getAddress());
        order.setPaymentStatus(PaymentStatus.COMPLETE);
        saveOrder(order);
        if(form.getCouponId() != null) {
            Coupon coupon = couponRepository.findById(form.getCouponId()).orElseThrow();
            coupon.setIsUsed(true);
            couponRepository.save(coupon);
        }
    }
    public ProductOrder issueOrder(ProductOrderForm form, Long productId, Member member) {
        ProductOrder productOrder = ProductOrder.builder()
                .orderCount(form.getOrderCount())
                .product(getProductById(productId))
                .member(member)
                .build();
        if(!canBuyProduct(productOrder.getProduct(), productOrder.getOrderCount())) {
            throw new RuntimeException("주문 수량이 주문 가능한 수량보다 많습니다.");
        }
        Product product = productOrder.getProduct();
        product.setStock(product.getStock() - productOrder.getOrderCount());
        saveProduct(product);
        return saveOrder(productOrder);
    }

    public ProductOrder saveOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
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
