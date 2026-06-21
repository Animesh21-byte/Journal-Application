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

    public List<User> findUserForSA(){
        Query query = new Query();
        /* query.addCriteria(Criteria.where("email").exists(true));
        query.addCriteria(Criteria.where("email").ne(null).ne("")); */
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"));//to check wether the email matches regular expression
        query.addCriteria(Criteria.where("roles").in("USER"));
        return mongoTemplate.find(query,User.class);
    }
}
