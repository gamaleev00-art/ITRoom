package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/admin/123")
    public String admin123() {
        return "admin123";
    }


}
