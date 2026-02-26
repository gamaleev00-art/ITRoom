package com.example.service;

import com.example.repository.UsersRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEventService {
    private final UsersRepo usersRepo;

    @Transactional
    public void failureLogin(String username) {
        usersRepo.findByUsername(username).ifPresent(user -> {
            int countFailLogin = user.getFailLoginCounter() + 1;
            System.out.println(countFailLogin);
            user.setFailLoginCounter(countFailLogin);
            if (countFailLogin >= 3) {
                user.setAccountNonLocked(false);
                log.info("Account {} is locked", username);
            }
        });
    }

    @Transactional
    public void successLogin(String username) {
        usersRepo.findByUsername(username).ifPresent(user -> {
            user.setFailLoginCounter(0);
            usersRepo.save(user);
        });
    }
}
