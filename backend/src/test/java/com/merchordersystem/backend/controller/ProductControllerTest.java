package com.merchordersystem.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merchordersystem.backend.dto.product.ProductRequest;
import com.merchordersystem.backend.model.Product;
import com.merchordersystem.backend.repository.ProductRepository;
import com.merchordersystem.backend.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
    private ObjectMapper objectMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    //測試用商品id
    private List<Product> testProducts;

    // 建立測試商品用
    private Product createTestProduct(String name, Double price) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setNumber(10);
        product.setDescription("就是" + name);
        product.setImageUrl("test.com");
        productRepository.save(product);

        return product;
    }

    // 建立測試商品用
    private Product createTestProductByRequest(String name, Double price) {

        ProductRequest request = new ProductRequest();
        request.setName(name);
        request.setPrice(price);
        request.setNumber(10);
        request.setDescription("測試用商品");
        request.setImageUrl("test.com");

        Integer productId = productService.createProduct(request);
        return productService.getProductById(productId);
    }

    @BeforeEach
    public void setup(){

        productRepository.deleteAll(); // 清空資料庫表
        testProducts = new ArrayList<>();

        Product product1 = createTestProduct("商品A", 100.0);
        Product product2 = createTestProduct("商品B", 200.0);
        Product product3 = createTestProduct("商品C", 300.0);
        Product product4 = createTestProduct("商品D", 400.0);
        Product product5 = createTestProduct("商品E", 500.0);

        testProducts.add(product1);
        testProducts.add(product2);
        testProducts.add(product3);
        testProducts.add(product4);
        testProducts.add(product5);

    }

    @Transactional
    @Test
    public void createProduct_success() throws Exception {
        // 前端發起request，以request的形式送進後端
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("襪子");
        productRequest.setPrice(50.0);
        productRequest.setNumber(10);
        productRequest.setDescription("套在腳上");
        productRequest.setImageUrl("test.com");

        //Java物件(productRequest) 轉 JSON格式
        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON) //告知發送的request中，content格式為何
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("襪子")))
                .andExpect(jsonPath("$.price", equalTo(50.0)))
                .andExpect(jsonPath("$.number", equalTo(10)))
                .andExpect(jsonPath("$.description", equalTo("套在腳上")))
                .andExpect(jsonPath("$.imageUrl", equalTo("test.com")));
    }

    @Transactional
    @Test
    public void createProduct_illegalArgument() throws Exception {
        // 前端發起request，以request的形式送進後端
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("耳機");

        //Java物件(productRequest) 轉 JSON格式
        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/products")
                .contentType(MediaType.APPLICATION_JSON) //告知發送的request中，content格式為何
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    public void updateProduct_success() throws Exception {
        //先建立商品
        Product testProduct = createTestProductByRequest("手套", 150.0);
        //再修改商品欄位（PUT就算只有修改部分欄位，也要一次覆蓋所有欄位）
        ProductRequest updateRequest = new ProductRequest();
        updateRequest.setName("新版手套");
        updateRequest.setPrice(200.0);
        updateRequest.setNumber(20);
        updateRequest.setDescription("升級版手套");
        updateRequest.setImageUrl("test.com");

        String json = objectMapper.writeValueAsString(updateRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", testProduct.getId())
                .contentType(MediaType.APPLICATION_JSON) //告知發送的request中，content格式為何
                .content(json);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", equalTo("新版手套")))
                .andExpect(jsonPath("$.price", equalTo(200.0)))
                .andExpect(jsonPath("$.number", equalTo(20)))
                .andExpect(jsonPath("$.description", equalTo("升級版手套")))
                .andExpect(jsonPath("$.imageUrl", equalTo("test.com")));
    }

    @Transactional
    @Test
    public void updateProduct_illegalArgument() throws Exception {
        //先建立商品
        Product testProduct = createTestProductByRequest("手套", 150.0);
        //修改商品欄位，假設有少給欄位
        ProductRequest updateRequest = new ProductRequest();
        updateRequest.setName("新版手套");


        String json = objectMapper.writeValueAsString(updateRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", testProduct.getId())
                .contentType(MediaType.APPLICATION_JSON) //告知發送的request中，content格式為何
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Transactional
    @Test
    public void updateProduct_productNotFound() throws Exception {

        ProductRequest updateRequest = new ProductRequest();
        updateRequest.setName("新版手套");
        updateRequest.setPrice(200.0);
        updateRequest.setNumber(20);
        updateRequest.setDescription("升級版手套");
        updateRequest.setImageUrl("test.com");

        String json = objectMapper.writeValueAsString(updateRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/products/{productId}", 2000)
                .contentType(MediaType.APPLICATION_JSON) //告知發送的request中，content格式為何
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Transactional
    @Test
    public void deleteProduct_success() throws Exception {

        Integer productId = testProducts.get(1).getId();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", productId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Transactional
    @Test
    public void deleteProduct_deleteNonExistingProduct() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/products/{productId}", 2000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204));
    }

    @Test
    public void getProduct_success() throws Exception {

        Integer productId = testProducts.get(1).getId();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products/{productId}", productId);
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("商品B"))) //這寫死了
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

    @Test
    public void getProducts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products");
        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)));
    }

    @Test
    public void getProducts_filtering() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("search", "商品B");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(1)));
    }

    @Test
    public void getProducts_sorting() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("search", "商品")
                .param("orderBy", "price")
                .param("sort", "desc");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(5)))
                .andExpect(jsonPath("$.results[0].price", equalTo(500.0)))
                .andExpect(jsonPath("$.results[4].price", equalTo(100.0)));
    }

    @Test
    public void getProducts_pagination() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/products")
                .param("orderBy", "price")
                .param("sort", "desc")
                .param("limit", "2")
                .param("offset", "2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.results", hasSize(2)))
                .andExpect(jsonPath("$.results[0].price", equalTo(300.0)))
                .andExpect(jsonPath("$.results[1].price", equalTo(200.0)));
    }

}