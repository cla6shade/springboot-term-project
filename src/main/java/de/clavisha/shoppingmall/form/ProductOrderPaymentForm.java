package de.clavisha.shoppingmall.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductOrderPaymentForm {
    @NotBlank(message = "주소 누락")
    private String address;

    private Long couponId;
    @Min(0)
    private Integer savingsUsageAmount;
}
