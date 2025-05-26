package com.merchordersystem.backend.repository;

import com.merchordersystem.backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>, JpaSpecificationExecutor<OrderItem> {
    // OrderItemRepository.java
    List<OrderItem> findByOrderId(Integer orderId);

}
