package com.example.listner;

import com.example.model.Users;
import com.example.repository.UsersRepo;
import com.example.service.AuthenticationEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationEventListener {

    private final AuthenticationEventService authEventService;

    @EventListener
    public void onAuthenticationFailure(AbstractAuthenticationFailureEvent event) {
        String username = event.getAuthentication().getName();
        log.info("Authentication Failure Event: {}", username);
        System.out.println("Не правильный логин ");
        authEventService.failureLogin(username);
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        if (event.getAuthentication() instanceof AnonymousAuthenticationToken){
            log.info("Anonymous Authentication Success");
            return;
        }
        log.info("Authentication Success Event: {}", event.getAuthentication().getName());
        String username = event.getAuthentication().getName();
        authEventService.successLogin(username);
    }
}
