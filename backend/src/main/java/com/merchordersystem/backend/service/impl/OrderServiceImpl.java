package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.dto.order.BuyItem;
import com.merchordersystem.backend.dto.order.CreateOrderRequest;
import com.merchordersystem.backend.model.Order;
import com.merchordersystem.backend.model.OrderStatus;
import com.merchordersystem.backend.model.Product;
import com.merchordersystem.backend.model.User;
import com.merchordersystem.backend.repository.OrderRepository;
import com.merchordersystem.backend.repository.ProductRepository;
import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        double totalPrice = 0.0;

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {
            Product product = productRepository.findById(buyItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            totalPrice += product.getPrice() * buyItem.getQuantity();
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return order.getId();

    }
}
