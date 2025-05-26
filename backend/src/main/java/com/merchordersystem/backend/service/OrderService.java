package com.merchordersystem.backend.service;

import com.merchordersystem.backend.dto.order.CreateOrderRequest;
import com.merchordersystem.backend.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);

}
