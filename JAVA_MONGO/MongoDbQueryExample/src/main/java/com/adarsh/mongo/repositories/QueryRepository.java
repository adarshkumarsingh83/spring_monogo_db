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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.adarsh.mongo.properties.PropertiesReader;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 2:09 PM
 * @see
 */

public interface QueryRepository {


    public void insertDummyDocuments();

    public void findOne();

    public void findAll();


    public void findByNam();

    public void findById();

    public void findByIds();

    public void findWhere1();

    public void findWhere2();

    public void findWhere3();

    public void findWhere4();

    public void dropDatabase();

}
