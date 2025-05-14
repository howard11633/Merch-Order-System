package com.merchordersystem.backend.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductQueryParams {

    private String search;
    private Double price;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;

}
