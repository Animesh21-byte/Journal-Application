package com.solutions.journalapp.service;

import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.repository.UserRepository;

@Component
public class UserService {
    @Autowired
    public UserRepository userRepository; 

    public void save(User user){
        userRepository.save(user);
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
