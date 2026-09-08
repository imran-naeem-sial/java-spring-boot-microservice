package com.app.ecom.Service;


import com.app.ecom.DTOs.ProductDTO;
import com.app.ecom.DTOs.ProductResponseDTO;
import com.app.ecom.Mapper.ProductMapper;
import com.app.ecom.Model.Product;
import com.app.ecom.Repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("product")
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    ProductService(ProductMapper productMapper, ProductRepository productRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
    }

    public ResponseEntity<ProductResponseDTO> create(ProductDTO productDTO) {

        if (productDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }


        Product product = productMapper.toEntityFromDto(productDTO);
        productRepository.save(product);


        return new ResponseEntity<>(productMapper.toResponseDTO(product),HttpStatus.CREATED);
    }

    public ResponseEntity<ProductResponseDTO> update(ProductDTO productDTO, long id) {

        return productRepository.findById(id)
                .map(p -> {
                    productMapper.updateProductFromDto(productDTO,p);
                    productRepository.save(p);
                    return new ResponseEntity<>(productMapper.toResponseDTO(p), HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<ProductResponseDTO> delete(long id) {

        return productRepository.findById(id)
                .map(p -> {
                    p.setIsActive(Boolean.FALSE);
                    productRepository.save(p);
                    return new ResponseEntity<>(productMapper.toResponseDTO(p), HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        return new ResponseEntity<>(productRepository.findAllByIsActiveIsTrue().stream()
                .map(productMapper::toResponseDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    public List<ProductResponseDTO> searchProducts(String keyword) {

        return productRepository.searchProducts(keyword).stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).stream().findFirst().orElse(null);
    }
}
