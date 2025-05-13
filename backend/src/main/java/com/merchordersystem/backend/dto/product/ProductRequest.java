package com.merchordersystem.backend.dto.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotNull
    private String name;

    @NotNull
    private double price;

    @NotNull
    private double number;

    @NotNull
    private String description;

}
