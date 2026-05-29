package com.solutions.journalapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.service.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @PostMapping
    public void insert(@RequestBody User entity) {
        userService.save(entity);
    }
    
    @PutMapping
    public void putMethodName(@RequestBody User entity) {
        User user = userService.findByUserName(entity.getUserName());
        if(user!=null){
            user.setUserName(entity.getUserName());
            user.setPassword(entity.getPassword());
            userService.save(user);
        }
    }
    

}
