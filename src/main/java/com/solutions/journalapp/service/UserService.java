package com.solutions.journalapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.repository.UserRepository;

@Component
public class UserService {
    @Autowired
    public UserRepository userRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User user){
        if (user.getPassword() != null && !isEncodedPassword(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(new ArrayList<>(List.of("USER")));
        }
        userRepository.save(user);
    }

    public void saveAdmin(User user){
        if (user.getPassword() != null && !isEncodedPassword(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(new ArrayList<>(List.of("ADMIN")));
        }
        userRepository.save(user);
    }

    private boolean isEncodedPassword(String password) {
        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Optional<User> getById(ObjectId id){
        return userRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public void deleteAll(){
        userRepository.deleteAll();
    }
    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
}
