package com.adarsh.mongo;

import com.adarsh.mongo.config.MongoConfigFactory;
import com.adarsh.mongo.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Adarsh_K
 * Date: 1/5/13
 * Time: 2:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    private static final ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfigFactory.class);

    @org.junit.Test
    public void testDelete() {
        MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");

        // insert 6 users for testing
        List<User> users = new ArrayList<User>();

        users.add(new User("1001", "ant", 10));
        users.add(new User("1002", "bird", 20));
        users.add(new User("1003", "cat", 30));
        users.add(new User("1004", "dog", 40));
        users.add(new User("1005", "elephant", 50));
        users.add(new User("1006", "frog", 60));
        mongoOperation.insert(users, User.class);

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("name").exists(true)
                .orOperator(Criteria.where("name").is("frog"), Criteria.where("name").is("dog")));
        mongoOperation.remove(query1, User.class);

        Query query2 = new Query();
        query2.addCriteria(Criteria.where("name").is("bird"));
        User userTest2 = mongoOperation.findOne(query2, User.class);
        mongoOperation.remove(userTest2);

        // The first document that matches the query is returned and also
        // removed from the collection in the
        // database.
        Query query3 = new Query();
        query3.addCriteria(Criteria.where("name").is("ant"));
        User userTest3 = mongoOperation.findAndRemove(query3, User.class);
        System.out.println("Deleted document : " + userTest3);

        // either cat or elephant is deleted, common mistake, dun use for batch
        // delete.
        /*
           * Query query4 = new Query(); query4.addCriteria(Criteria
           * .where("name") .exists(true)
           * .orOperator(Criteria.where("name").is("cat"),
           * Criteria.where("name").is("elephant")));
           * mongoOperation.findAndRemove(query1, User.class);
           * System.out.println("Deleted document : " + userTest4);
           */

        System.out.println("\nAll users : ");
        List<User> allUsers = mongoOperation.findAll(User.class);
        for (User user : allUsers) {
            System.out.println(user);
        }
        mongoOperation.dropCollection(User.class);
    }
}
