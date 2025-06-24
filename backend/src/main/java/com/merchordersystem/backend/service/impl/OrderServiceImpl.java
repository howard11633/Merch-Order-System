package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.Specification.OrderSpecification;
import com.merchordersystem.backend.Specification.UserSpecification;
import com.merchordersystem.backend.dto.order.BuyItem;
import com.merchordersystem.backend.dto.order.CreateOrderRequest;
import com.merchordersystem.backend.dto.order.OrderQueryParams;
import com.merchordersystem.backend.model.*;
import com.merchordersystem.backend.repository.OrderItemRepository;
import com.merchordersystem.backend.repository.OrderRepository;
import com.merchordersystem.backend.repository.ProductRepository;
import com.merchordersystem.backend.repository.UserRepository;
import com.merchordersystem.backend.service.OrderService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()) {

            //先找到該商品
            Product product = productRepository.findById(buyItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            //如果訂單商品數量少於庫存，無法建立訂單
            if (product.getNumber() < buyItem.getQuantity()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            product.setNumber(product.getNumber() - buyItem.getQuantity());
            productRepository.save(product);

            double price = product.getPrice() * buyItem.getQuantity();
            totalPrice += price;

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(price);
            orderItem.setProduct(product);
            orderItem.setQuantity(buyItem.getQuantity());

            orderItem.setProductName(product.getName());
            orderItem.setImageUrl(product.getImageUrl());

            orderItemList.add(orderItem);

        }

        orderItemRepository.saveAll(orderItemList);  // 儲存每筆訂單項目

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return order.getId();

    }

    @Override
    public Order getOrderById(Integer orderId) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            throw new RuntimeException("Order not found");  // 或自訂例外
        }

        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {

        Integer userId = orderQueryParams.getUserId();
        Integer limit = orderQueryParams.getLimit();
        Integer offset = orderQueryParams.getOffset();

        int page = offset / limit;
        Pageable pageable = PageRequest.of(page, limit, Sort.by("orderDate").descending());

        Page<Order> orderPage = orderRepository.findByUserId(userId, pageable);
        List<Order> orderList = orderPage.getContent();

        for (Order order : orderList) {
            List<OrderItem> orderItemList =orderItemRepository.findByOrderId(order.getId());
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Long countOrder(OrderQueryParams orderQueryParams) {
        Integer userId = orderQueryParams.getUserId();
        return orderRepository.countByUserId(userId);
    }
}
