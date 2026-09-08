package com.app.ecom.DTOs;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
@Data
public class CartItemRequest {

    @NotNull()
    private Long productId;
    private Integer quantity;
}
