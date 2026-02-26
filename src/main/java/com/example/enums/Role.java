package com.example.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER"),MODERATOR("MODERATOR"),SUPER_ADMIN("SUPER_ADMIN");

    Role(String name) {
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
