package de.clavisha.shoppingmall.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MyInfoUpdateForm {
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(max = 255, message = "이름은 최대 255자까지 입력할 수 있습니다.")
    private String name;

    @Size(max = 32, message = "연락처는 최대 32자까지 입력할 수 있습니다.")
    private String contact;
}
