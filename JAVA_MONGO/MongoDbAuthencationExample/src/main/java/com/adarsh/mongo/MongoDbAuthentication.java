/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo;
/*
 * {@inheritDoc}
*/

/**
 * @Product : MongoDbAuthentication provide implementation of the specification
 *                    provided for the ...    
 */

import java.net.UnknownHostException;
import java.util.Date;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 1:44 PM
 * @see
 */

public class MongoDbAuthentication {
    public static void main(String[] args) {

        try {

            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("admin");

            boolean auth = db.authenticate("admin", "pwd".toCharArray());
            if (auth) {
                DBCollection table = db.getCollection("user");
                BasicDBObject document = new BasicDBObject();
                document.put("name", "adarsh");
                table.insert(document);

                System.out.println("Login is successful!");
            } else {
                System.out.println("Login is failed!");
            }
            System.out.println("Done");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
