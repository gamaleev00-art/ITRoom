package com.example.contoller;

import com.example.dto.UpdateEmailRequest;
import com.example.dto.UpdateNameRequest;
import com.example.model.Order;
import com.example.model.User;
import com.example.service.OrderService;
import com.example.service.UserService;
import com.example.views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class JsonViewController {
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public JsonViewController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    @JsonView(Views.UserSummary.class)
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id}")
    @JsonView(Views.UserDetails.class)
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PatchMapping("/users/{id}/email")
    @JsonView(Views.UserSummary.class)
    public User updateUserEmail(@PathVariable Long id,
                                @RequestBody UpdateEmailRequest email){
        userService.updateUserEmail(id, email);
        return userService.getUserById(id);
    }

    @PatchMapping("/users/{id}/name")
    @JsonView(Views.UserSummary.class)
    public User updateUserName(@PathVariable Long id,
                               @RequestBody UpdateNameRequest name){
        userService.updateUserName(id,name);
        return userService.getUserById(id);
    }
}

