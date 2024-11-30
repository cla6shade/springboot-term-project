package de.clavisha.shoppingmall.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductReviewForm {
    @NotBlank(message = "제목 누락")
    private String title;

    @NotBlank(message = "내용 입력")
    private String content;

    @NotNull(message = "별점을 선택해주세요.")
    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private Byte stars;
}
