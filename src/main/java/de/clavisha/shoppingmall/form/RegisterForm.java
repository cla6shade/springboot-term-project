package de.clavisha.shoppingmall.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterForm {
    @NotBlank(message = "로그인 id 누락")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String loginId;

    @NotBlank(message = "비밀번호 누락")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "이름 누락")
    private String name;

    @NotBlank(message = "전화번호 누락")
    private String contact;
}
