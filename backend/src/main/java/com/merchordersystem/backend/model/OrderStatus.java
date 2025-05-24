package com.merchordersystem.backend.model;

public enum OrderStatus {

    PENDING,        // 訂單已建立，等待處理
    CONFIRMED,      // 訂單已確認（例如付款完成）
    PROCESSING,     // 訂單處理中（例如商品打包中）
    SHIPPED,        // 訂單已出貨
    DELIVERED,      // 訂單已送達
    CANCELLED,      // 訂單已取消
    RETURNED,       // 訂單退貨
    REFUNDED        // 訂單退款完成
}
