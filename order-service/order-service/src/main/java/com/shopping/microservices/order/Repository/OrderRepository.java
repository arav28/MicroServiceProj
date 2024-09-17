package com.shopping.microservices.order.Repository;

import com.shopping.microservices.order.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
