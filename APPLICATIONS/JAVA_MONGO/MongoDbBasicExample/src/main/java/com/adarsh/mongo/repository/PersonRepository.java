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
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 1:30 PM
 * @see
 */

public interface PersonRepository {

    public String insert(final Person person) throws Exception;

    public void findAndDisplay(final String personName) throws Exception;

    public void findAndDisplayByQuery(final String personName) throws Exception;

    public String update(final String personName, final Person newPerson) throws Exception;

    public String deletePerson(final String personName) throws Exception;

    public long personCount(final String personName) throws Exception;
}
