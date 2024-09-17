package com.shopping.microservices.order.Controller;

import com.shopping.microservices.order.Service.OrderService;
import com.shopping.microservices.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.saveOrder(orderRequest);
        return "Order Successfully Placed";
    }
}
