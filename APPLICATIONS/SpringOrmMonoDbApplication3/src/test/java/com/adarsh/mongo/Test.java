package com.adarsh.mongo;

import com.adarsh.mongo.config.MongoConfigFactory;
import com.adarsh.mongo.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Adarsh_K
 * Date: 1/5/13
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    private static final ApplicationContext APPLICATION_CONTEXT = new AnnotationConfigApplicationContext(MongoConfigFactory.class);

    @org.junit.Test
    public void insertTest() {

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
        User userC = new User("3000", "metallica", 34, new Date());
        User userD = new User("4000", "metallica", 34, new Date());
        User userE = new User("5000", "metallica", 34, new Date());
        List<User> userList = new ArrayList<User>();
        userList.add(userC);
        userList.add(userD);
        userList.add(userE);
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


    @org.junit.Test
    public void updateTest() {
        MongoOperations mongoOperation = (MongoOperations) APPLICATION_CONTEXT.getBean("mongoTemplate");

        // insert 6 users for testing
        List<User> users = new ArrayList<User>();

        users.add(new User("1001", "appleA", 20, new Date()));
        users.add(new User("1002", "appleB", 20, new Date()));
        users.add(new User("1003", "appleC", 20, new Date()));
        users.add(new User("1004", "appleD", 20, new Date()));
        users.add(new User("1005", "appleE", 20, new Date()));
        users.add(new User("1006", "appleF", 20, new Date()));
        mongoOperation.insert(users, User.class);

        // Case 1 ... find and update
        System.out.println("Case 1");

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("name").is("appleA"));

        User userTest1 = mongoOperation.findOne(query1, User.class);

        System.out.println("userTest1 - " + userTest1);

        userTest1.setAge(99);
        mongoOperation.save(userTest1);

        User userTest1_1 = mongoOperation.findOne(query1, User.class);

        System.out.println("userTest1_1 - " + userTest1_1);

        // Case 2 ... select single field only
        System.out.println("\nCase 2");

        Query query2 = new Query();
        query2.addCriteria(Criteria.where("name").is("appleB"));
        query2.fields().include("name");

        User userTest2 = mongoOperation.findOne(query2, User.class);
        System.out.println("userTest2 - " + userTest2);

        userTest2.setAge(99);

        mongoOperation.save(userTest2);

        // ooppss, you just override everything, it caused ic=null and
        // createdDate=null

        Query query2_1 = new Query();
        query2_1.addCriteria(Criteria.where("name").is("appleB"));

        User userTest2_1 = mongoOperation.findOne(query2_1, User.class);
        System.out.println("userTest2_1 - " + userTest2_1);

        System.out.println("\nCase 3");
        Query query3 = new Query();
        query3.addCriteria(Criteria.where("name").is("appleC"));
        query3.fields().include("name");

        User userTest3 = mongoOperation.findOne(query3, User.class);
        System.out.println("userTest3 - " + userTest3);

        Update update3 = new Update();
        update3.set("age", 100);

        mongoOperation.updateFirst(query3, update3, User.class);

        Query query3_1 = new Query();
        query3_1.addCriteria(Criteria.where("name").is("appleC"));

        User userTest3_1 = mongoOperation.findOne(query3_1, User.class);
        System.out.println("userTest3_1 - " + userTest3_1);

        System.out.println("\nCase 4");
        Query query4 = new Query();
        query4.addCriteria(Criteria
                .where("name")
                .exists(true)
                .orOperator(Criteria.where("name").is("appleD"),
                        Criteria.where("name").is("appleE")));
        Update update4 = new Update();
        update4.set("age", 11);
        update4.unset("createdDate");

        // update 1004 only.
        // mongoOperation.updateFirst(query4, update4, User.class);

        // update all matched
        mongoOperation.updateMulti(query4, update4, User.class);

        System.out.println(query4.toString());

        List<User> usersTest4 = mongoOperation.find(query4, User.class);

        for (User userTest4 : usersTest4) {
            System.out.println("userTest4 - " + userTest4);
        }

        System.out.println("\nCase 5");
        Query query5 = new Query();
        query5.addCriteria(Criteria.where("name").is("appleZ"));

        Update update5 = new Update();
        update5.set("age", 21);

        mongoOperation.upsert(query5, update5, User.class);

        User userTest5 = mongoOperation.findOne(query5, User.class);
        System.out.println("userTest5 - " + userTest5);

        System.out.println("\nCase 6");
        Query query6 = new Query();
        query6.addCriteria(Criteria.where("name").is("appleF"));

        Update update6 = new Update();
        update6.set("age", 101);
        update6.set("ic", 1111);

        User userTest6 = mongoOperation.findAndModify(query6, update6,
                new FindAndModifyOptions().returnNew(true), User.class);
        System.out.println("userTest6 - " + userTest6);

        mongoOperation.dropCollection(User.class);
    }
}
