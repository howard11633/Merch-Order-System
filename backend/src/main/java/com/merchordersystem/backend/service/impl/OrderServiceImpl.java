package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.dto.order.BuyItem;
import com.merchordersystem.backend.dto.order.CreateOrderRequest;
import com.merchordersystem.backend.model.*;
import com.merchordersystem.backend.repository.OrderItemRepository;
import com.merchordersystem.backend.repository.OrderRepository;
import com.merchordersystem.backend.repository.ProductRepository;
import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.service.OrderService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    //加入log機制：slf4j logger
    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    //設計多張Table之行為，避免數字不一致 (All or Never)
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        double totalPrice = 0.0;
        List<OrderItem> orderItemList = new ArrayList<>();

        //新增一筆訂單
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productRepository.findById(buyItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            double price = product.getPrice() * buyItem.getQuantity();
            totalPrice += price;

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(price);
            orderItem.setProduct(product);
            orderItem.setQuantity(buyItem.getQuantity());

            orderItemList.add(orderItem);

        }

        orderItemRepository.saveAll(orderItemList);  // 儲存每筆訂單項目

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return order.getId();

    }
}
