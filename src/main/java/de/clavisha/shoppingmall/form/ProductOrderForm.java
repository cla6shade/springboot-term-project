package de.clavisha.shoppingmall.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductOrderForm {
    @NotBlank(message = "주소 누락")
    private String address;

    @NotNull(message = "주문 개수 누락")
    @Min(value = 1, message = "주문 개수는 1개 이상이어야 합니다.")
    private Short orderCount;

    // 추후 구현
    private Short couponId;
    @Min(0)
    private Integer savingsUsageAmount;
}
