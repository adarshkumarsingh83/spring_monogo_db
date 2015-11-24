/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo.repositores;
/*
 * {@inheritDoc}
*/

/**
 * @Product : ImageRepositoryImpl provide implementation of the specification
 *                    provided for the ...    
 */

import com.adarsh.mongo.properties.PropertiesReader;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 1:48 PM
 * @see
 */

public interface ImageRepository {

    //"src\\main\\resources\\upload\\Home.png"
    public void saveImage(final String image,final String newImage)throws Exception;


    public void displayImages();

    //"src\\main\\resources\\download\\HomeNew.png"
    public void getImages(final String image,final String newImage)throws Exception;

    public void removeImage(final String fileName)throws Exception;
}
