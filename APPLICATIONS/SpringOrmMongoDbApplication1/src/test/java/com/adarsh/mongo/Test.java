package com.adarsh.mongo;

import com.adarsh.mongo.config.MongoConfigFactory;
import com.adarsh.mongo.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Adarsh_K
 * Date: 1/5/13
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    private static final ApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(MongoConfigFactory.class);

    @org.junit.Test
    public void test() {

        MongoOperations mongoOperation = (MongoOperations) APPLICATION_CONTEXT.getBean("mongoTemplate");

        // insert 6 users for testing
        List<User> users = new ArrayList<User>();
        users.add(new User("1001", "ant", 10));
        users.add(new User("1002", "bird", 20));
        users.add(new User("1003", "cat", 30));
        users.add(new User("1004", "dog", 40));
        users.add(new User("1005", "elephant", 50));
        users.add(new User("1006", "frog", 60));
        mongoOperation.insert(users, User.class);

        System.out.println("Case 1 - find with BasicQuery example");

        BasicQuery query1 = new BasicQuery("{ age : { $lt : 40 }, name : 'cat' }");
        User userTest1 = mongoOperation.findOne(query1, User.class);

        System.out.println("query1 - " + query1.toString());
        System.out.println("userTest1 - " + userTest1);

        System.out.println("\nCase 2 - find example");

        Query query2 = new Query();
        query2.addCriteria(Criteria.where("name").is("dog").and("age").is(40));

        User userTest2 = mongoOperation.findOne(query2, User.class);
        System.out.println("query2 - " + query2.toString());
        System.out.println("userTest2 - " + userTest2);

        System.out.println("\nCase 3 - find list $inc example");

        List<Integer> listOfAge = new ArrayList<Integer>();
        listOfAge.add(10);
        listOfAge.add(30);
        listOfAge.add(40);

        Query query3 = new Query();
        query3.addCriteria(Criteria.where("age").in(listOfAge));

        List<User> userTest3 = mongoOperation.find(query3, User.class);
        System.out.println("query3 - " + query3.toString());

        for (User user : userTest3) {
            System.out.println("userTest3 - " + user);
        }

        System.out.println("\nCase 4 - find list $and $lt, $gt example");

        Query query4 = new Query();

        // Due to limitations of the com.mongodb.BasicDBObject,
        // you can't add a second 'age' expression specified as 'age : { "$gt" :
        // 10}'. Criteria already contains 'age : { "$lt" : 40}'.
        // query4.addCriteria(Criteria.where("age").lt(40).and("age").gt(10));

        query4.addCriteria(Criteria.where("age").lt(40).andOperator(Criteria.where("age").gt(10)));

        List<User> userTest4 = mongoOperation.find(query4, User.class);
        System.out.println("query4 - " + query4.toString());

        for (User user : userTest4) {
            System.out.println("userTest4 - " + user);
        }

        System.out.println("\nCase 5 - find list and sorting example");
        Query query5 = new Query();
        query5.addCriteria(Criteria.where("age").gte(30));
        query5.with(new Sort(Sort.Direction.DESC, "age"));

        List<User> userTest5 = mongoOperation.find(query5, User.class);
        System.out.println("query5 - " + query5.toString());

        for (User user : userTest5) {
            System.out.println("userTest5 - " + user);
        }

        System.out.println("\nCase 6 - find by regex example");
        Query query6 = new Query();
        query6.addCriteria(Criteria.where("name").regex("D.*G", "i"));

        List<User> userTest6 = mongoOperation.find(query6, User.class);
        System.out.println("query6 - " + query6.toString());

        for (User user : userTest6) {
            System.out.println("userTest6 - " + user);
        }

        mongoOperation.dropCollection(User.class);

    }
}
