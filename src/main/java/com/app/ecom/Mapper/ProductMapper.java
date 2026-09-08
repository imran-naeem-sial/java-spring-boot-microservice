package com.app.ecom.Mapper;

import com.app.ecom.DTOs.ProductDTO;
import com.app.ecom.DTOs.ProductResponseDTO;
import com.app.ecom.Model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProductMapper {

    // CREATE: DTO → new Product
    Product toEntityFromDto(ProductDTO productDTO);

    // CREATE: ResponseDTO → new Product
    Product toEntityFromResponseDto(ProductResponseDTO productResponseDTO);

    // UPDATE: DTO → existing Product
    void updateProductFromDto(ProductDTO productDTO, @MappingTarget Product existingProduct);

    // UPDATE: ResponseDTO → existing Product
    void updateProductFromResponseDto(ProductResponseDTO productResponseDTO, @MappingTarget Product existingProduct);

    // CONVERT: Product → ProductDTO (for responses)
    ProductDTO toDTO(Product product);

    // CONVERT: Product → ProductDTO (for responses)
    ProductResponseDTO toResponseDTO(Product product);
}
