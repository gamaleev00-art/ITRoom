package com.example.contoller;

import com.example.dto.UpdateEmailRequest;
import com.example.dto.UpdateNameRequest;
import com.example.exception.UserNotFoundException;
import com.example.model.Order;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.OrderService;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JsonViewController.class)
class JsonViewControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private OrderService orderService;


    private String json;

    User user = new User();
    Order order = new Order();

    @BeforeEach
    void setUp() {

        user.setId(1L);
        user.setName("test");
        user.setEmail("test@gmail.com");

        order.setId(1L);
        order.setStatus("Доставлен");
        order.setTotalPrice(BigDecimal.valueOf(1000.00));

        user.setOrders(List.of(order));
    }

    @Test
    public void testGetUsers_shouldReturnUserSummary() throws Exception {
        when(userService.getAllUsers())
                .thenReturn(List.of(user));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("test"))
                .andExpect(jsonPath("$[0].email").value("test@gmail.com"))
                .andExpect(jsonPath("$[0].orders").doesNotExist());
    }

    @Test
    public void testGetOrders_shouldReturnUserDetails() throws Exception {
        when(userService.getUserById(1L))
                .thenReturn(user);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.orders").isArray())
                .andExpect(jsonPath("$.orders[0].id").value(1))
                .andExpect(jsonPath("$.orders[0].status").value("Доставлен"))
                .andExpect(jsonPath("$.orders[0].totalPrice").value(1000.00));
    }

    @Test
    public void testDeleteUser_shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(userService).deleteUserById(1L);
    }

    @Test
    public void testCreateUser_shouldCreateUser() throws Exception {
        when(userService.createUser(any(User.class)))
                .thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.email").value("test@gmail.com"));
    }

    @Test
    public void testUpdateUserEmail() throws Exception {
        UpdateEmailRequest email = new UpdateEmailRequest("new@gmail.com");

        user.setEmail("new@gmail.com");

        when(userService.getUserById(1L))
                .thenReturn(user);

        mockMvc.perform(patch("/users/{id}/email", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("new@gmail.com"))
                .andExpect(jsonPath("$.orders").doesNotExist());

        verify(userService).updateUserEmail(eq(1L), any(UpdateEmailRequest.class));
    }

    @Test
    public void testUpdateNameEmail() throws Exception {
        UpdateNameRequest name = new UpdateNameRequest("newName");

        user.setName("newName");

        when(userService.getUserById(1L))
                .thenReturn(user);

        mockMvc.perform(patch("/users/{id}/name", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(name)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("newName"))
                .andExpect(jsonPath("$.orders").doesNotExist());

        verify(userService).updateUserName(eq(1L), any(UpdateNameRequest.class));
    }

    @Test
    public void testCreateUSer_shouldReturn400_InvalidEmail() throws Exception {
        user.setEmail("invalid");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUser_shouldReturn400_whenUserNotFound() throws Exception {
        when(userService.getUserById(2L))
                .thenThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(get("/users/2"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testDeleteUser_shouldReturn400_whenUserNotFound() throws Exception {
        doThrow(new UserNotFoundException("User not found"))
                .when(userService).deleteUserById(2L);

        mockMvc.perform(delete("/users/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_shouldReturn415_whenWrongContentType() throws Exception {

        mockMvc.perform(post("/users")
                        .content("some text"))
                .andExpect(status().isUnsupportedMediaType());
    }
}