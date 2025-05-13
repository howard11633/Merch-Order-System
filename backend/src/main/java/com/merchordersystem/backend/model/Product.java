package com.merchordersystem.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "number")
    private double number;

    @Column(name = "description")
    private String description;

    @Column(name ="created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_modified_time")
    private LocalDateTime lastModifiedTime;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.lastModifiedTime = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastModifiedTime = LocalDateTime.now();
    }

}
