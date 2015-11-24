/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo.repositories;
/*
 * {@inheritDoc}
*/

/**
 * @Product : QueryRepository provide implementation of the specification
 *                    provided for the ...    
 */

import com.adarsh.mongo.properties.PropertiesReader;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 2:09 PM
 * @see
 */

public class QueryRepositoryImpl implements QueryRepository{

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryRepositoryImpl.class);
    private static MongoClient mongoClient;
    private static DB database;
    private static DBCollection dbCollection;

    static {
        try {
            init();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void init() throws Exception {
        /*
        Mongo mongo = new Mongo(PropertiesReader.getProperty("dataSource.hostname")
                           , PropertiesReader.getProperty("dataSource.portNumber"));
        DB db = mongo.getDB(PropertiesReader.getProperty("dataSource.database"));
        DBCollection collection = db.getCollection(PropertiesReader.getProperty("dataSource.collection"));
        */

        mongoClient = new MongoClient(PropertiesReader.getProperty("dataSource.hostname")
                , Integer.parseInt(PropertiesReader.getProperty("dataSource.portNumber")));
        database = mongoClient.getDB(PropertiesReader.getProperty("dataSource.database"));
        dbCollection = database.getCollection(PropertiesReader.getProperty("dataSource.collection"));
    }


    public  void insertDummyDocuments() {
        List<DBObject> list = new ArrayList<DBObject>();
        Calendar cal = Calendar.getInstance();
        for (int i = 1; i <= 5; i++) {
            BasicDBObject data = new BasicDBObject();
            data.append("number", i);
            data.append("name", "adarsh-" + i);
            cal.add(Calendar.DATE, 1);
            list.add(data);
        }
        dbCollection.insert(list);
    }


    public void findOne(){
        LOGGER.info("1. Find first matched document");
        DBObject dbObject = dbCollection.findOne();
        LOGGER.info(dbObject.toString());
    }

    public void findAll(){
        LOGGER.info("\n1. Find all matched documents");
        DBCursor cursor = dbCollection.find();
        while (cursor.hasNext()) {
            LOGGER.info(cursor.next().toString());
        }
    }


    public void findByNam(){
        LOGGER.info("\n1. Get 'name' field only");
        BasicDBObject allQuery = new BasicDBObject();
        BasicDBObject fields = new BasicDBObject();
        fields.put("name", 1);
        DBCursor cursor2 = dbCollection.find(allQuery, fields);
        while (cursor2.hasNext()) {
            LOGGER.info(cursor2.next().toString());
        }
    }

    public void findById(){
        LOGGER.info("\n2. Find where number = 5");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("number", 5);
        DBCursor cursor3 = dbCollection.find(whereQuery);
        while (cursor3.hasNext()) {
            LOGGER.info(cursor3.next().toString());
        }
    }

    public void findByIds(){
        LOGGER.info("\n2. Find where number in 2,4 and 5");
        BasicDBObject inQuery = new BasicDBObject();
        List<Integer> list = new ArrayList<Integer>();
        list.add(2);
        list.add(4);
        list.add(5);
        inQuery.put("number", new BasicDBObject("$in", list));
        DBCursor cursor4 = dbCollection.find(inQuery);
        while (cursor4.hasNext()) {
            LOGGER.info(cursor4.next().toString());
        }
    }

    public void findWhere1(){
        LOGGER.info("\n2. Find where 5 > number > 2");
        BasicDBObject gtQuery = new BasicDBObject();
        gtQuery.put("number", new BasicDBObject("$gt", 2).append("$lt", 5));
        DBCursor cursor5 = dbCollection.find(gtQuery);
        while (cursor5.hasNext()) {
            LOGGER.info(cursor5.next().toString());
        }
    }

    public void findWhere2(){
        LOGGER.info("\n2. Find where number != 4");
        BasicDBObject neQuery = new BasicDBObject();
        neQuery.put("number", new BasicDBObject("$ne", 4));
        DBCursor cursor6 = dbCollection.find(neQuery);
        while (cursor6.hasNext()) {
            LOGGER.info(cursor6.next().toString());
        }
    }

    public void findWhere3(){
        LOGGER.info("\n3. Find when number = 2 and name = 'adarsh-2' example");
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("number", 2));
        obj.add(new BasicDBObject("name", "adarsh-2"));
        andQuery.put("$and", obj);
        LOGGER.info(andQuery.toString());
        DBCursor cursor7 = dbCollection.find(andQuery);
        while (cursor7.hasNext()) {
            LOGGER.info(cursor7.next().toString());
        }
    }

    public void findWhere4(){
        LOGGER.info("\n4. Find where name = 'ada.*-[1-3]', case sensitive example");
        BasicDBObject regexQuery = new BasicDBObject();
        regexQuery.put("name",
                new BasicDBObject("$regex", "ada.*-[1-3]")
                        .append("$options", "i"));
        LOGGER.info(regexQuery.toString());

        DBCursor cursor8 = dbCollection.find(regexQuery);
        while (cursor8.hasNext()) {
            LOGGER.info(cursor8.next().toString());
        }
    }

    public void dropDatabase(){
        dbCollection.drop();
    }

}
