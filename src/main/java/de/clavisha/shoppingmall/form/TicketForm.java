package de.clavisha.shoppingmall.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TicketForm {
    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Content is required.")
    private String content;

    private Long productId;
}