package com.merchordersystem.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "status")
    private String name;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name ="order_date")
    private LocalDateTime orderDate;



    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
    }


}
