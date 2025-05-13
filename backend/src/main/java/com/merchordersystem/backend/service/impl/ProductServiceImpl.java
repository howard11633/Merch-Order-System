package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.dto.product.ProductRequest;
import com.merchordersystem.backend.model.Product;
import com.merchordersystem.backend.repository.ProductRepository;
import com.merchordersystem.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    //加入log機制：slf4j logger
    private final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setNumber(productRequest.getNumber());
        product.setDescription(productRequest.getDescription());
        // 存進 DB
        productRepository.save(product);

        // 回傳主鍵 ID（或你要回傳 DTO 都可）
        return product.getId();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);//沒有.orElse會出錯
    }

    //刪
    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    //改
    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product.setNumber(productRequest.getNumber());
            product.setDescription(productRequest.getDescription());
            productRepository.save(product);
        }
    }


}
