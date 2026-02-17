package com.example.service;

import com.example.dto.UpdateEmailRequest;
import com.example.dto.UpdateNameRequest;
import com.example.exception.UserNotFoundException;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void updateUserEmail(Long id, UpdateEmailRequest email){
        userRepository.updateEmail(id, email.email());
    }

    public void updateUserName(Long id, UpdateNameRequest name){
        userRepository.updateName(id, name.name());
    }

    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
}
