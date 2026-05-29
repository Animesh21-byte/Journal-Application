package com.solutions.journalapp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.solutions.journalapp.entity.User;


public interface UserRepository extends MongoRepository<User,ObjectId>{
    User findByUserName(String username);
}
