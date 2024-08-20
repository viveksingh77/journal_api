package com.firstspringboot.firstspringboot.repository;

import com.firstspringboot.firstspringboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();
      //  query.addCriteria(Criteria.where("userName").is("test2"));
 //       Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(
//                Criteria.where("email").exists(true),
//                Criteria.where("sentimentalAnalysis").is(true)
//        ));

    //    query.addCriteria(Criteria.where("email").exists(true));
 //       query.addCriteria(Criteria.where("email").ne(""));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z|A-Z]{2,6}$"));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
