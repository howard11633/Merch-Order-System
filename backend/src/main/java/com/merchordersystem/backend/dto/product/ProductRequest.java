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
    private Double price;

    @NotNull
    private Integer number;

    @NotNull
    private String description;

    @NotNull
    private String imageUrl;

}
