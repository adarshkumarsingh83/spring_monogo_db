/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo;
/*
 * {@inheritDoc}
*/

/**
 * @Product : TestQuery provide implementation of the specification
 *                    provided for the ...    
 */


import com.adarsh.mongo.properties.PropertiesReader;
import com.adarsh.mongo.repositories.QueryRepository;
import com.adarsh.mongo.repositories.QueryRepositoryImpl;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 18/6/13 10:33 AM
 * @see
 */

public class TestQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestQuery.class);
    private static final QueryRepository QUERY_REPOSITORY = new QueryRepositoryImpl();
    private static MongoClient mongo;
    private static DB db;

    @Before
    public void init() throws Exception {
        mongo = new MongoClient(PropertiesReader.getProperty("dataSource.hostname")
                , Integer.parseInt(PropertiesReader.getProperty("dataSource.portNumber")));
        db = mongo.getDB(PropertiesReader.getProperty("dataSource.database"));
    }


    @Test
    public void test() {
        try {
            boolean auth = db.authenticate(PropertiesReader.getProperty("dataSource.username")
                    , PropertiesReader.getProperty("dataSource.password").toCharArray());
            if (auth) {

                QUERY_REPOSITORY.insertDummyDocuments();

                QUERY_REPOSITORY.findOne();

                QUERY_REPOSITORY.findAll();

                QUERY_REPOSITORY.findByNam();

                QUERY_REPOSITORY.findById();

                QUERY_REPOSITORY.findByIds();

                QUERY_REPOSITORY.findWhere1();

                QUERY_REPOSITORY.findWhere2();

                QUERY_REPOSITORY.findWhere3();

                QUERY_REPOSITORY.findWhere4();

                QUERY_REPOSITORY.dropDatabase();

                LOGGER.info("Done Operations");
            } else {
                throw new RuntimeException("Authentication failure");
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
