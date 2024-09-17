package com.shopping.microservices.order.Service;

import com.shopping.microservices.order.Client.InventoryClient;
import com.shopping.microservices.order.Model.Order;
import com.shopping.microservices.order.Repository.OrderRepository;
import com.shopping.microservices.order.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void saveOrder(OrderRequest orderRequest){

        var checkProductAvailability = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(checkProductAvailability){
            Order order =  new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);
        }
        else {
            throw new RuntimeException("Product is not available");
        }


    }
}
