package com.solutions.journalapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.repository.UserRepository;
import org.slf4j.Logger;

@Component
public class UserService {
    @Autowired
    public UserRepository userRepository; 

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public void save(User user){
        if (user.getPassword() != null && !isEncodedPassword(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(new ArrayList<>(List.of("USER")));
        }
        userRepository.save(user);
        logger.info("User Created Successfully");

    }

    public void saveAdmin(User user){
        if (user.getPassword() != null && !isEncodedPassword(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(new ArrayList<>(List.of("ADMIN")));
        }
        userRepository.save(user);
        logger.info("Admin Created Successfully");
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
        logger.info("Deleted User Successfully");

    }
    public void deleteAll(){
        userRepository.deleteAll();
        logger.info("Deleted All Users Successfully");
    }
    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
}
