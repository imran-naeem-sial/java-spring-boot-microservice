package com.app.ecom.Service;

import com.app.ecom.DTOs.OrderItemResponseDTO;
import com.app.ecom.DTOs.OrderResponseDTO;
import com.app.ecom.Enums.OrderStatus;
import com.app.ecom.Model.*;
import com.app.ecom.Repository.OrderRepository;
import com.app.ecom.Utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartService cartService;
    private final UserService userService;
    private final OrderRepository ordersRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Response create(String userId) {

        User user = new User();
        try {
            user.setId(Long.parseLong(userId));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Response.getBadRequest(String.format("The userId %s can't be parsed to a number", userId));
        }

        List<CartItem> cartItems = cartService.findAllCartItemsByUser(user);
        if(cartItems.isEmpty()) {
            return Response.getOkRequest("The User does not have any active carts ", userId);
        }

        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalPrice);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItems = cartItems.stream()
                .map(ci -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(ci.getQuantity());
                    orderItem.setPrice(ci.getPrice());
                    orderItem.setProduct(ci.getProduct());
                    return orderItem;
                }).toList();

        order.setOrderItems(orderItems);
        Order result = ordersRepository.save(order);

        cartService.clearCart(user);

        List<OrderItemResponseDTO> itemResponseDTOS = orderItems.stream()
                .map(oi -> {
                    OrderItemResponseDTO orderItemResponseDTO = new OrderItemResponseDTO(
                            oi.getId(),
                            oi.getProduct().getId(),
                            oi.getQuantity(),
                            oi.getPrice()
                    );
                    return orderItemResponseDTO;
                }).toList();

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .id(result.getId())
                .orderStatus(result.getOrderStatus())
                .totalAmount(result.getTotalAmount())
                .orderItemResponses(itemResponseDTOS)
                .build();
        return new Response(true, "Order Created Successfully", orderResponseDTO,200);
    }


}
