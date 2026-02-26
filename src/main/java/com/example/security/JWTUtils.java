package com.example.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class JWTUtils {

    // Ключ шифрования для JWT

    private final SecretKey secretKey;

    // Время действия токена в миллисекундах (24 часа)
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = ACCESS_TOKEN_EXPIRATION_TIME * 7;

    public JWTUtils(@Value("${jwt.secret}") String sKey) {
        // Строка, используемая для создания секретного ключа
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(sKey));
    }

    /*Метод для генерации JWT токена на основе данных пользователя*/
    public String generateToken(UserDetails userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .and()
                .signWith(secretKey)
                .compact();
    }

    // Метод для генерации токена обновления (refresh token) с дополнительными данными
    public String generateRefreshToken(UserDetails userDetails) {
        if (userDetails.isAccountNonExpired() && userDetails.isAccountNonLocked()) {
            return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                    .signWith(secretKey)
                    .compact();
        }
        return "Доступ запрещен";
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Метод для извлечения имени пользователя из токена
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claimsTFunction.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}

