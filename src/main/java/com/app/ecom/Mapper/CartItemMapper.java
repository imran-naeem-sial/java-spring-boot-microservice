package com.app.ecom.Mapper;

import com.app.ecom.DTOs.CartItemResponse;
import com.app.ecom.Model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CartItemMapper {

    CartItemResponse toResponseDTO(CartItem cartItem);
}
