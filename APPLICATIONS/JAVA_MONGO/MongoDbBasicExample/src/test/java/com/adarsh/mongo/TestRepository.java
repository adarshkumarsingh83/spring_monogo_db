/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo;
/*
 * {@inheritDoc}
*/

/**
 * @Product : TestRepository provide implementation of the specification
 *                    provided for the ...    
 */


import com.adarsh.mongo.entites.Person;
import com.adarsh.mongo.properties.PropertiesReader;
import com.adarsh.mongo.repository.PersonRepository;
import com.adarsh.mongo.repository.PersonRepositoryImpl;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.adarsh.mongo.repository.PersonRepository.*;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 4:12 PM
 * @see
 */

public class TestRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRepository.class);
    private static final PersonRepository PERSON_REPOSITORY=new PersonRepositoryImpl();
    private static MongoClient mongo;
    private static DB db;

    @Before
    public void init() throws Exception {
        mongo = new MongoClient(PropertiesReader.getProperty("dataSource.hostname")
                , Integer.parseInt(PropertiesReader.getProperty("dataSource.portNumber")));
        db = mongo.getDB(PropertiesReader.getProperty("dataSource.database"));
    }

    @Test
    public void masterTest() {
        try {
            boolean auth = db.authenticate(PropertiesReader.getProperty("dataSource.username")
                    , PropertiesReader.getProperty("dataSource.password").toCharArray());
            if (auth) {
                Person person=new Person("Adarsh kumar","30",new Date().toString());
                LOGGER.info(PERSON_REPOSITORY.insert(person));
                PERSON_REPOSITORY.findAndDisplay("Adarsh kumar");
                LOGGER.info(PERSON_REPOSITORY.update("adarsh kumar ", new Person("Adarsh kumar singh", "30", new Date().toString())));
                PERSON_REPOSITORY.findAndDisplayByQuery("Adarsh kumar singh");
                LOGGER.info(PERSON_REPOSITORY.deletePerson("Adarsh kumar"));
                LOGGER.info("Person Count "+PERSON_REPOSITORY.personCount("Adarsh kumar"));
                /**** Done ****/
                LOGGER.info("Done Operations");
            } else {
                throw new RuntimeException("Authentication failure");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    @Test
    public void insertTest() {
        try {
            boolean auth = db.authenticate(PropertiesReader.getProperty("dataSource.username")
                    , PropertiesReader.getProperty("dataSource.password").toCharArray());
            if (auth) {
                Person person=new Person("Adarsh kumar","30",new Date().toString());
                LOGGER.info(PERSON_REPOSITORY.insert(person));
            } else {
                throw new RuntimeException("Authentication failure");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }


    @Test
    public void updateTest() {
        try {
            boolean auth = db.authenticate(PropertiesReader.getProperty("dataSource.username")
                    , PropertiesReader.getProperty("dataSource.password").toCharArray());
            if (auth) {
                LOGGER.info(PERSON_REPOSITORY.update("adarsh kumar singh", new Person("Adarsh kumar", "30", new Date().toString())));
            } else {
                throw new RuntimeException("Authentication failure");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }


    @Test
    public void deleteTest() {
        try {
            boolean auth = db.authenticate(PropertiesReader.getProperty("dataSource.username")
                    , PropertiesReader.getProperty("dataSource.password").toCharArray());
            if (auth) {
                LOGGER.info("Person Count "+ (PERSON_REPOSITORY.personCount("adarsh kumar")));
                LOGGER.info(PERSON_REPOSITORY.deletePerson("adarsh kumar"));
                LOGGER.info("Person Count "+ (PERSON_REPOSITORY.personCount("adarsh kumar")));
            } else {
                throw new RuntimeException("Authentication failure");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Test
    public void countTest() {
        try {
            boolean auth = db.authenticate(PropertiesReader.getProperty("dataSource.username")
                    , PropertiesReader.getProperty("dataSource.password").toCharArray());
            if (auth) {
                LOGGER.info("Person Count "+ (PERSON_REPOSITORY.personCount("adarsh kumar")));
            } else {
                throw new RuntimeException("Authentication failure");
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
