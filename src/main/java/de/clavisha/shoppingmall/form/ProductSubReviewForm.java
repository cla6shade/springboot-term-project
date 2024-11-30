package de.clavisha.shoppingmall.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductSubReviewForm {

    @NotNull(message = "리뷰 누락")
    private Long parentReviewId;

    @NotBlank(message = "댓글 내용 누락")
    private String content;
}
