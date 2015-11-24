package com.adarsh.mongo.test;

import com.adarsh.mongo.model.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Adarsh_K
 * Date: 1/5/13
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlConfigTest {

    private static final ApplicationContext APPLICATION_CONTEXT = new GenericXmlApplicationContext("classpath:/spring/applicationContext-config.xml");

    @Test
    public void test() {

        MongoOperations mongoOperation = (MongoOperations) APPLICATION_CONTEXT.getBean("mongoTemplate");

        User user = new User("adarsh", "password123");
        // save
        mongoOperation.save(user);

        // now user object got the created id.
        System.out.println("1. user : " + user);

        // query to search user
        Query searchUserQuery = new Query(Criteria.where("username").is("adarsh"));

        // find the saved user again.
        User savedUser = mongoOperation.findOne(searchUserQuery, User.class);
        System.out.println("2. find - savedUser : " + savedUser);

        // update password
        mongoOperation.updateFirst(searchUserQuery, Update.update("password", "new password"),
                User.class);

        // find the updated user object
        User updatedUser = mongoOperation.findOne(
                new Query(Criteria.where("username").is("adarsh")), User.class);

        System.out.println("3. updatedUser : " + updatedUser);

        // delete
        mongoOperation.remove(searchUserQuery, User.class);

        // List, it should be empty now.
        List<User> listUser = mongoOperation.findAll(User.class);
        System.out.println("4. Number of user = " + listUser.size());
    }
}
