package com.merchordersystem.backend.service;

import com.merchordersystem.backend.dto.product.ProductRequest;
import com.merchordersystem.backend.dto.user.UserRequest;
import com.merchordersystem.backend.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProduct(Integer productId);

}
