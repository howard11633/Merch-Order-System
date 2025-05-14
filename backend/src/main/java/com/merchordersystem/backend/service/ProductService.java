package com.merchordersystem.backend.service;

import com.merchordersystem.backend.dto.product.ProductQueryParams;
import com.merchordersystem.backend.dto.product.ProductRequest;
import com.merchordersystem.backend.dto.user.UserRequest;
import com.merchordersystem.backend.model.Product;

import java.util.List;

public interface ProductService {

    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);

    //動態查詢
    List<Product> getProducts(ProductQueryParams productQueryParams);
    Long countProduct(ProductQueryParams productQueryParams);
}
