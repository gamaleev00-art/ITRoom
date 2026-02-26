package com.example.service;

import com.example.model.UserPrincipal;
import com.example.model.Users;
import com.example.repository.UsersRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepo usersRepo;

    public MyUserDetailsService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(username));
        return new UserPrincipal(user);
    }
}
