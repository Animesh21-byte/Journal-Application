package com.solutions.journalapp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.solutions.journalapp.cache.AppCache;
import com.solutions.journalapp.entity.User;
import com.solutions.journalapp.repository.MongoRepositoryImpl;
import com.solutions.journalapp.service.UserService;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;





@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @Autowired
    private MongoRepositoryImpl mongoRepositoryImpl;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAll();
        if(allUsers!=null && !allUsers.isEmpty()){
            return new ResponseEntity<>(allUsers,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/all-users")
    public ResponseEntity<?> deleteAllUsers(){
        try{
            userService.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteByUserName(@PathVariable ObjectId id){
        try{
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        try{
            userService.saveAdmin(user);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @GetMapping("/clear-cache")
    public void clearCache(){
        appCache.init();
    }

    @GetMapping("/findbyname/{userName}")
    public List<User> getMethodName(@PathVariable String userName) {
        return mongoRepositoryImpl.findByUserName(userName);
    }

    @GetMapping("/findUsersWithSA")
    public List<User> getUsersWithSA(){
        return mongoRepositoryImpl.findUserForSA();
    }
    
}