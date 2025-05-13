package com.merchordersystem.backend.controller;

import com.merchordersystem.backend.dto.product.ProductRequest;
import com.merchordersystem.backend.model.Product;
import com.merchordersystem.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){

        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    //修改使用者資料
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                           @RequestBody @Valid ProductRequest productRequest){
        Product product = getProduct(productId);

        if (product != null) {
            productService.updateProduct(productId, productRequest);
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //刪除使用者
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //查詢單一使用者
    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }


}
