package com.solutions.journalapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.service.QuoteService;
import com.solutions.journalapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private QuoteService quoteService;

    @GetMapping("/greet")
    public ResponseEntity<?> greet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Hello " + auth.getName() + "\n"+quoteService.getQuote());
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
