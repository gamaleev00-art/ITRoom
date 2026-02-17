package com.example.service;

import com.example.model.Product;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public String productInfo(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->
                new RuntimeException("Product not found")
                );
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка создания json");
        }
    }

    public Product createProduct(String json){
        try {
            return productRepository.save(objectMapper.readValue(json, Product.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка создания json");
        }
    }

    public String updateProduct(Long id, String json) {
        try {
            if (!productRepository.existsById(id)) {
                throw new RuntimeException("Product not found");
            }
            Product productUpdate = objectMapper.readValue(json, Product.class);
            productUpdate.setProductId(id);

            Product saved = productRepository.save(productUpdate);
            return objectMapper.writeValueAsString(saved);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка обработки JSON", e);
        }
    }

    public void deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(()->
                new RuntimeException("Product not found")
        );
        productRepository.delete(product);
    }
}
