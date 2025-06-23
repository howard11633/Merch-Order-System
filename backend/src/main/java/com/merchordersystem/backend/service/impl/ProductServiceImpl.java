package com.merchordersystem.backend.service.impl;

import com.merchordersystem.backend.Specification.ProductSpecification;
import com.merchordersystem.backend.dto.product.ProductQueryParams;
import com.merchordersystem.backend.dto.product.ProductRequest;
import com.merchordersystem.backend.model.Product;
import com.merchordersystem.backend.repository.ProductRepository;
import com.merchordersystem.backend.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

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
        product.setImageUrl(productRequest.getImageUrl());

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

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {

        String orderBy = productQueryParams.getOrderBy();
        String sort = productQueryParams.getSort();
        Integer limit = productQueryParams.getLimit();
        Integer offset = productQueryParams.getOffset();

        Sort.Direction direction = sort.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.by(direction, orderBy));
        Specification<Product> spec = ProductSpecification.build(productQueryParams); // Filtering
        Page<Product> springPage = productRepository.findAll(spec, pageable);

        // 如果完全沒參數 → 全部使用者
        return springPage.getContent();
    }

    @Override
    public Long countProduct(ProductQueryParams productQueryParams) {
        Specification<Product> spec = ProductSpecification.build(productQueryParams); // Filtering
        return productRepository.count(spec);
    }




}
