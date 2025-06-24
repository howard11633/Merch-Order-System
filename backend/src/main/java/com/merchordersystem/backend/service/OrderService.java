package com.merchordersystem.backend.service;

import com.merchordersystem.backend.dto.order.CreateOrderRequest;
import com.merchordersystem.backend.dto.order.OrderQueryParams;
import com.merchordersystem.backend.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Long countOrder(OrderQueryParams orderQueryParams);

}
