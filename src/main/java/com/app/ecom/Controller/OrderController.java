package com.app.ecom.Controller;

import com.app.ecom.Service.OrderService;
import com.app.ecom.Utils.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Response create(@RequestHeader("X-User-ID") String userId){
        return orderService.create(userId);
    }
}
