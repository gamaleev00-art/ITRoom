package com.example.controller;


import com.example.model.Order;
import com.example.model.Product;
import com.example.service.OrderService;
import com.example.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody String json) {
        return ResponseEntity.ok(productService.createProduct(json));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id,
                                                @RequestBody String json) {
        return ResponseEntity.ok(productService.updateProduct(id, json));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable Long id) {
        return productService.productInfo(id);
    }


}
