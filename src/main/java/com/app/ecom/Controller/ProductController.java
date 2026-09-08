package com.app.ecom.Controller;

import com.app.ecom.DTOs.ProductDTO;
import com.app.ecom.DTOs.ProductResponseDTO;
import com.app.ecom.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductDTO productDTO) {
         return productService.create(productDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getProducts() {
        return productService.getProducts();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductDTO productDTO, @PathVariable long id) {
        return productService.update(productDTO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> delete(@PathVariable long id) {
        return productService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

}
