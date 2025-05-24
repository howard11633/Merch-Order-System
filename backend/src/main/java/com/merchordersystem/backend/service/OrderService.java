package com.merchordersystem.backend.service;

import com.merchordersystem.backend.dto.order.CreateOrderRequest;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);


}
