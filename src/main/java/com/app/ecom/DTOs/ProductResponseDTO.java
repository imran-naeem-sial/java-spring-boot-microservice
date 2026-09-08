package com.app.ecom.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductResponseDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String url;
    private String category;
    private Boolean isActive;
}
