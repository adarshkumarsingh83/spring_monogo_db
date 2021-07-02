package com.adarsh.monog;

import com.adarsh.mongo.config.MongoConfigFactory;
import com.adarsh.mongo.user.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Adarsh_K
 * Date: 1/5/13
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    private static final ApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(MongoConfigFactory.class);

    @org.junit.Test
    public void test() {

        MongoOperations mongoOperation = (MongoOperations) APPLICATION_CONTEXT.getBean("mongoTemplate");

        // Case1 - insert a user, put "tableA" as collection name
        System.out.println("Case 1...");
        User userA = new User("1000", "apple", 54, new Date());
        mongoOperation.save(userA, "tableA");

        // find
        Query findUserQuery = new Query();
        findUserQuery.addCriteria(Criteria.where("ic").is("1000"));
        User userA1 = mongoOperation.findOne(findUserQuery, User.class, "tableA");
        System.out.println(userA1);

        // Case2 - insert a user, put entity as collection name
        System.out.println("Case 2...");
        User userB = new User("2000", "orange", 64, new Date());
        mongoOperation.save(userB);

        // find
        User userB1 = mongoOperation.findOne(new Query(Criteria.where("age").is(64)), User.class);
        System.out.println(userB1);

        // Case3 - insert a list of users
        System.out.println("Case 3...");

        List<User> userList = new ArrayList<User>();
        userList.add(new User("3000", "metallica", 34, new Date()));
        userList.add(new User("4000", "metallica", 34, new Date()));
        userList.add(new User("5000", "metallica", 34, new Date()));
        mongoOperation.insert(userList, User.class);

        // find
        List<User> users = mongoOperation.find(new Query(Criteria.where("name").is("metallica")),
                User.class);

        for (User temp : users) {
            System.out.println(temp);
        }

        //save vs insert
        System.out.println("Case 4...");
        User userD1 = mongoOperation.findOne(new Query(Criteria.where("age").is(64)), User.class);
        userD1.setName("new name");
        userD1.setAge(100);

        //E11000 duplicate key error index, _id existed
        //mongoOperation.insert(userD1);
        mongoOperation.save(userD1);
        User userE1 = mongoOperation.findOne(new Query(Criteria.where("age").is(100)), User.class);
        System.out.println(userE1);

    }
}
