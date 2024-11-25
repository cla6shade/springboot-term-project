package de.clavisha.shoppingmall.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Setter
    @Transient
    private boolean isSelected;

}
