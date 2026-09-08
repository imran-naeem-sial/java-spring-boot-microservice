package com.app.ecom.Controller;

import com.app.ecom.Service.OrderService;
import com.app.ecom.Utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    public Response create(@RequestHeader("X-User-ID") String userId){
        return orderService.create(userId);
    }
}
