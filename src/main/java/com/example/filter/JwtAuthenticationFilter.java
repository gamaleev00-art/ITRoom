package com.example.security;

import com.example.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    private MyUserDetailsService myUserDetailsService;

    // Метод, выполняемый для каждого HTTP запроса
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("!!! ФИЛЬТР СРАБОТАЛ ДЛЯ: " + request.getRequestURI());
        String token = null;
        String username = null;
        // Шаг 1: Извлечение заголовка авторизации из запроса
        String header = request.getHeader("Authorization");
        // Шаг 2: Проверка наличия заголовка авторизации
        if(header != null && header.startsWith("Bearer ")) {
            // Шаг 3: Извлечение токена из заголовка
            token = header.substring(7);
        // Шаг 4: Извлечение имени пользователя из JWT токена
            username = jwtUtils.extractUsername(token);
        }
        // Шаг 5: Проверка валидности токена и аутентификации
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("Пытаемся аутентифицировать пользователя: {}", username);
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            if (jwtUtils.isTokenValid(token,userDetails)) {
                // Шаг 6: Создание нового контекста безопасности
                log.info("Токен валиден. Устанавливаем Authentication в контекст. Роли: {}", userDetails.getAuthorities());
                UsernamePasswordAuthenticationToken tokenAuthentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                tokenAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
            } else{
                log.warn("Токен НЕ валиден для пользователя: {}", username);
            }
        }
        // Шаг 7: Передача запроса на дальнейшую обработку в фильтрующий цепочке
        filterChain.doFilter(request,response);
    }
}
