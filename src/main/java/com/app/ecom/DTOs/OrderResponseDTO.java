package com.app.ecom.DTOs;


import com.app.ecom.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private List<OrderItemResponseDTO> orderItemResponses;
}
