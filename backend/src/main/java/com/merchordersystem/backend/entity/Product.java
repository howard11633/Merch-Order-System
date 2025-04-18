package com.merchordersystem.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private double number;

    public Product() {}

    public Product(String name, double price, double number) {
        this.name = name;
        this.price = price;
        this.number = number;
    }

    // Getters and Setters
    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
