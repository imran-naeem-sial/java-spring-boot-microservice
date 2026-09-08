package com.app.ecom.Controller;

import com.app.ecom.DTOs.CartItemRequest;
import com.app.ecom.DTOs.CartItemResponse;
import com.app.ecom.Service.CartService;
import com.app.ecom.Utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest
    ){
        if(cartService.addToCart(userId,cartItemRequest)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.badRequest().body("Product not found or product is out of stock or user not found");

    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(
            @RequestHeader("X-User-ID") String userId,
            @PathVariable Long productId
    ){
        if(cartService.removeFromCart(userId, productId)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.badRequest().body("Product or user not found");

    }

    @GetMapping("")
    public Response getCart(
            @RequestHeader("X-User-ID") String userId
    ){

        return cartService.fetchCartItems(userId);

    }
}
