package com.example.controller;

import com.example.model.Order;
import com.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void shouldCreateOrder() throws Exception {
        String orderJson = "{\"shippingAddress\":\"Main St 1\"}";
        Order order = new Order();
        UUID orderId = UUID.randomUUID();
        order.setOrderId(orderId);

        when(orderService.createOrder(anyString())).thenReturn(order);

        mockMvc.perform(post("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(orderJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").exists());
    }

    @Test
    void shouldGetOrder() throws Exception {
        UUID orderId = UUID.randomUUID();
        String expectedJson = "{\"orderId\":\"" + orderId + "\", \"status\":\"NEW\"}";

        when(orderService.getOrderInfo(orderId)).thenReturn(expectedJson);

        mockMvc.perform(get("/api/v1/order/" + orderId))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}