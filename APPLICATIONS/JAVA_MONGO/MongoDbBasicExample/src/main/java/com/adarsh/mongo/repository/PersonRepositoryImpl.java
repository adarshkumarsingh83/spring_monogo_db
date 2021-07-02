/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo.repository;
/*
 * {@inheritDoc}
*/

/**
 * @Product : PersonRepository provide implementation of the specification
 *                    provided for the ...    
 */

import com.adarsh.mongo.entites.Person;
import com.adarsh.mongo.properties.PropertiesReader;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 1:30 PM
 * @see
 */

public class PersonRepositoryImpl implements PersonRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonRepositoryImpl.class);
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
        mongoClient = new MongoClient(PropertiesReader.getProperty("dataSource.hostname")
                , Integer.parseInt(PropertiesReader.getProperty("dataSource.portNumber")));
        database = mongoClient.getDB(PropertiesReader.getProperty("dataSource.database"));
        dbCollection = database.getCollection(PropertiesReader.getProperty("dataSource.collection"));
    }


    public String insert(final Person person) throws Exception {
        /**** Insert ****/
        // create a document to store key and value
        BasicDBObject document = new BasicDBObject();
        document.put("name", person.getName());
        document.put("age", person.getAge());
        document.put("createdDate", person.getCreatedDate());
        WriteResult writeResult=dbCollection.insert(document);
        String error=writeResult.getError();
        return ((error!=null)?"Exception Generated "+error:"Successful Insertion Operation "+writeResult.toString());
    }


    public void findAndDisplay(final String personName) throws Exception {
        /**** Find and display ****/
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", personName);
        DBCursor cursor = dbCollection.find(searchQuery);
        while (cursor.hasNext()) {
            LOGGER.info(cursor.next().toString());
        }
    }

    public void findAndDisplayByQuery(final String personName) throws Exception {
        /**** Find and display ****/
        BasicDBObject searchQuery
                = new BasicDBObject().append("name", personName);
        DBCursor cursor = dbCollection.find(searchQuery);
        while (cursor.hasNext()) {
            LOGGER.info(cursor.next().toString());
        }
    }

    public String update(final String personName, final Person newPerson) throws Exception {
        /**** Update ****/
        BasicDBObject query = new BasicDBObject();
        query.put("name", personName);
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("name", newPerson.getName());
        newDocument.put("age", newPerson.getAge());
        newDocument.put("createdDate", newPerson.getCreatedDate());
        BasicDBObject updateObj = new BasicDBObject();
        updateObj.put("$set", newDocument);
        WriteResult writeResult=dbCollection.update(query, updateObj);
        String error=writeResult.getError();
        return ((error!=null)?"Exception Generated "+error:"Successful Updating Operation "+writeResult.toString());
    }


    public String deletePerson(final String personName) throws Exception {
        BasicDBObject query = new BasicDBObject();
        query.put("name", personName);
        WriteResult writeResult=dbCollection.remove(query);
        String error=writeResult.getError();
       return ((error!=null)?"Exception Generated "+error:"Successful Deletion Operation "+writeResult.toString());
    }


    public long personCount(final String personName) throws Exception {
        BasicDBObject query = new BasicDBObject();
        query.put("name", personName);
        return dbCollection.count(query);
    }

}
