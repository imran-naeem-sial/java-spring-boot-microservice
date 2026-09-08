package com.app.ecom.Service;

import com.app.ecom.DTOs.CartItemRequest;
import com.app.ecom.DTOs.CartItemResponse;
import com.app.ecom.Mapper.CartItemMapper;
import com.app.ecom.Model.CartItem;
import com.app.ecom.Model.Product;
import com.app.ecom.Model.User;
import com.app.ecom.Repository.CartItemRepository;
import com.app.ecom.Utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("cart")
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final UserService userService;
    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {

        Product product = productService.findProductById(cartItemRequest.getProductId());
        if(product == null) {
            return false;
        }

        User user = userService.findUserById(Long.valueOf(userId));
        if(user == null) {
            return false;
        }

        CartItem cartItem = findCartByUserAndProduct(user, product);

        int newQty = cartItemRequest.getQuantity();
        if(cartItem != null){
            newQty += cartItem.getQuantity();
        } else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setUser(user);
        }
        if(product.getQuantity() < newQty) {
            return false;
        }
        cartItem.setQuantity(newQty);
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

        cartItemRepository.save(cartItem);
        return true;

    }

    public CartItem findCartByUserAndProduct(User user, Product product) {
        return cartItemRepository.findFirstByUserAndProduct(user, product);
    }

    public List<CartItem> findAllCartItemsByUser(User user) {
        return cartItemRepository.findAllByUser(user);
    }

    public boolean removeFromCart(String userId, Long productId) {

        if(userId == null || userId.isBlank() || productId == null || productId <= 0) {
            return false;
        }

        User cartUser = userService.findUserById(Long.valueOf(userId));
        if(cartUser == null) {
            return false;
        }
        Product product = new Product();
        product.setId(productId);

        CartItem cartItem = findCartByUserAndProduct(cartUser, product);
        if(cartItem==null) {
            return false;
        }

        cartItemRepository.delete(cartItem);
        return true;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Response clearCart(User user) {
        return cartItemRepository.deleteByUser(user) > 0 ?
                Response.getOkRequest("Deleted") : Response.getBadRequest("Failed to delete!");
    }

    public Response fetchCartItems(String userId) {

        if(userId == null || userId.isEmpty()) {
            return Response.getBadRequest("Please provide the userId.");
        }
        long uId = 0;
        try {
            uId = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            return Response.getBadRequest(String.format("ID %s is not a valid number!", userId));
        }

        User user = new User();
        user.setId(uId);

        List<CartItem> cartItems = cartItemRepository.findAllByUser(user);
        List<CartItemResponse> cartItemResponses = new ArrayList<>();
//        cartItems.stream().map(ci -> {cartItemMapper.toResponseDTO(ci);}).toList();

        for(CartItem cartItem: cartItems) {
            CartItemResponse cartItemResponse = cartItemMapper.toResponseDTO(cartItem);
            cartItemResponse.setUsername(cartItem.getUser().getFirstName());
            cartItemResponse.setProductSku(cartItem.getProduct().getName());
            cartItemResponses.add(cartItemResponse);
        }

        return Response.getOkRequest(cartItemResponses);
    }

//    public User findAllByUser(Long id) {
//        return cartItemRepository.findFirstById(id).orElse(null);
//    }
}
