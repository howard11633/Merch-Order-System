package com.merchordersystem.backend.controller;

import com.merchordersystem.backend.model.Product;
import com.merchordersystem.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    private Integer productId;

    @BeforeEach
    public void setup(){
        Product product = new Product();
        product.setName("頭巾");
        product.setPrice(300.0);
        product.setNumber(10.0);
        product.setDescription("套在頭上");
        productRepository.save(product);
        productId = product.getId();
        System.out.println(product);
    }

    @Test
    public void getProduct_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", productId);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("頭巾")))
                .andExpect(jsonPath("$.price", notNullValue()))
                .andExpect(jsonPath("$.number", notNullValue()))
                .andExpect(jsonPath("$.description", notNullValue()))
                .andExpect(jsonPath("$.createdAt", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedTime", notNullValue()));
    }

    @Test
    public void getProduct_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", 2000);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(404));
    }

}