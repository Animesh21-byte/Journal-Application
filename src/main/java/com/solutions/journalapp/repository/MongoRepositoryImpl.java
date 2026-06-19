package com.solutions.journalapp.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import com.solutions.journalapp.entity.User;

@Component
public class MongoRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> findByUserName(String userName){
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(userName));
        return mongoTemplate.find(query,User.class);
    }
}
