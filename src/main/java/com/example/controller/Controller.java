package com.example.controller;

import com.example.service.SocialAppService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {

    private final SocialAppService socialAppService;

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal,
                       Model model) {
        model.addAttribute("name", principal.getAttribute("name"));
        model.addAttribute("email", principal.getAttribute("email"));
        model.addAttribute("id", principal.getAttribute("id"));
        model.addAttribute("login", principal.getAttribute("login"));
        return "user";
    }

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @PatchMapping("/user/{id}")
    public void giveAdminRole(@PathVariable Long id){
        socialAppService.giveAdminRole(id);
    }
}
