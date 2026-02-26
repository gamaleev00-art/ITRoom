package com.example.controller;

import com.example.enums.Role;
import com.example.model.Users;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HomePageController {

    private final UserService userService;

    @PostMapping("/registry")
    public Users registry(@RequestBody Map<String,String> payload) {
        return userService.createUser(
                payload.get("username"),
                payload.get("password")
        );
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        System.out.println("Мы зашли на этот метод");
        return userService.login(user);
    }

    @GetMapping("/homepage")
    public String homepage() {
        return "Hello World";
    }

    @GetMapping("/user")
    public String userPage() {
        log.warn("Мы зашли на этот метод");
        return "Hello User";
    }

    @GetMapping("/product")
    public String products() {
        return "Hello Products";
    }

    @GetMapping("/product/{id}")
    public String productPage(@PathVariable Integer id) {
        return "Hello Product";
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/users")
    public List<Users> users() {
        return userService.findAll();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/user/{id}/edit")
    public Users editUser(@PathVariable Long id,
                         @RequestBody Users user) {
        return userService.editUser(id,user);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/user/{id}/unlock")
    public Users unlockUser(@PathVariable Long id) {
        return userService.unlockUser(id);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/product/{id}/edit")
    public String productEdit(@PathVariable Integer id){
        return "Да вы модератор и может быть когда то здесь появятся продукты и у вас будет работа";
    }

}
