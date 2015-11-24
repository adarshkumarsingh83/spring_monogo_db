/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo;
/*
 * {@inheritDoc}
*/

/**
 * @Product : UpdateExample provide implementation of the specification
 *                    provided for the ...    
 */

import java.net.UnknownHostException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 2:04 PM
 * @see
 */

public class UpdateExample {

    public static void printAllDocuments(DBCollection collection) {
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void removeAllDocuments(DBCollection collection) {
        collection.remove(new BasicDBObject());
    }

    public static void insertDummyDocuments(DBCollection collection) {
        BasicDBObject document = new BasicDBObject();
        document.put("hosting", "hostA");
        document.put("type", "vps");
        document.put("clients", 1000);

        BasicDBObject document2 = new BasicDBObject();
        document2.put("hosting", "hostB");
        document2.put("type", "dedicated server");
        document2.put("clients", 100);

        BasicDBObject document3 = new BasicDBObject();
        document3.put("hosting", "hostC");
        document3.put("type", "vps");
        document3.put("clients", 900);

        collection.insert(document);
        collection.insert(document2);
        collection.insert(document3);
    }

    public static void main(String[] args) {

        try {

            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("mongodb");

            // get a single collection
            DBCollection collection = db.getCollection("mongodbCollection");

            System.out.println("Testing 1...no $set");

            insertDummyDocuments(collection);

            // find hosting = hostB, and update the clients to 110
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.put("clients", 110);

            BasicDBObject searchQuery = new BasicDBObject().append("hosting", "hostB");

            collection.update(searchQuery, newDocument);

            printAllDocuments(collection);
            removeAllDocuments(collection);

            System.out.println("\nTesting 1...with $set");

            insertDummyDocuments(collection);

            BasicDBObject updateDocument = new BasicDBObject();
            updateDocument.append("$set", new BasicDBObject().append("clients", 110));

            BasicDBObject searchQuery2 = new BasicDBObject().append("hosting", "hostB");

            collection.update(searchQuery2, updateDocument);

            printAllDocuments(collection);
            removeAllDocuments(collection);

            System.out.println("\nTesting 2... with $inc");
            insertDummyDocuments(collection);
            // find hosting = hostB and increase it's "clients" value by 99
            BasicDBObject newDocument2 = new BasicDBObject().append("$inc",
                    new BasicDBObject().append("clients", 99));

            collection.update(new BasicDBObject().append("hosting", "hostB"), newDocument2);

            printAllDocuments(collection);
            removeAllDocuments(collection);

            System.out.println("\nTesting 3... with $multi");

            insertDummyDocuments(collection);
            // find type = vps , update all matched documents , clients value to 888
            BasicDBObject updateQuery = new BasicDBObject();
            updateQuery.append("$set", new BasicDBObject().append("clients", "888"));

            BasicDBObject searchQuery3 = new BasicDBObject();
            searchQuery3.append("type", "vps");

            collection.updateMulti(searchQuery3, updateQuery);
            // collection.update(searchQuery3, updateQuery, false, true);

            printAllDocuments(collection);
            removeAllDocuments(collection);

            System.out.println("Done");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }

    }
}
