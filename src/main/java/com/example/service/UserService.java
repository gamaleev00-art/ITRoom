package com.example.service;

import com.example.enums.Role;
import com.example.exception.UserNotFoundException;
import com.example.model.Users;
import com.example.repository.UsersRepo;
import com.example.security.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepo usersRepo;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Users> findAll() {
        return usersRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Users findById(Long id) {
        return usersRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("Users with id " + id + " not found!"));
    }
    @Transactional
    public Users editUser(Long id, Users users) {
        Users existUser = usersRepo.findById(id).orElseThrow(() ->
                new UserNotFoundException("Users with id " + id + " not found!"));
        if (users.getUsername() != null) {
            existUser.setUsername(users.getUsername());
        }
        if (users.getPassword() != null) {
            existUser.setPassword(passwordEncoder.encode(users.getPassword()));
        }
        if (users.getRole() != null) {
            existUser.setRole(users.getRole());
        }
        return existUser;
    }

    @Transactional
    public String login(Users user) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return jwtUtils.generateToken(userDetails);
    }

    @Transactional
    public Users createUser(String username, String password) {
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        return usersRepo.save(user);
    }

    @Transactional
    public Users editUserRole(Long id, Role role) {
        Users user = usersRepo.findById(id).orElseThrow(()->
                new UserNotFoundException("Users with id " + id + " not found!"));

        user.setRole(role);
        return usersRepo.save(user);
    }

    @Transactional
    public Users unlockUser(Long id) {
        Users user = usersRepo.findById(id).orElseThrow(()->
                new UserNotFoundException("Users with id " + id + " not found!"));
        user.setAccountNonLocked(true);
        user.setFailLoginCounter(0);
        return usersRepo.save(user);
    }
}
