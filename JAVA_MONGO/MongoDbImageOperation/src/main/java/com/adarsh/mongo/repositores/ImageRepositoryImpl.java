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

import java.io.File;

import com.adarsh.mongo.properties.PropertiesReader;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 17/6/13 1:48 PM
 * @see
 */

public class ImageRepositoryImpl implements ImageRepository{

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageRepositoryImpl.class);
    private static DB database;
    private static String imageBucket;
    private static MongoClient mongoClient;
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
        imageBucket =PropertiesReader.getProperty("dataSource.bucket");
        database = mongoClient.getDB(PropertiesReader.getProperty("dataSource.database"));
        dbCollection = database.getCollection(PropertiesReader.getProperty("dataSource.collection"));
    }

    public void saveImage(final String image,final String newImage)throws Exception{
        final File imageFile = new File(image);
        // create a "photo" namespace
        GridFS gfsPhoto = new GridFS(database, imageBucket);
        // get image file from local drive
        GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
        // set a new filename for identify purpose
        gfsFile.setFilename(newImage);
        // save the image file into mongoDB
        gfsFile.save();
    }

    public void displayImages(){
        GridFS gfsPhoto = new GridFS(database, imageBucket);
        // print the result
        DBCursor cursor = gfsPhoto.getFileList();
        while (cursor.hasNext()) {
            LOGGER.info(cursor.next().toString());
        }
    }

    public void getImages(final String image,final String newImage)throws Exception{
        GridFS gfsPhoto = new GridFS(database, imageBucket);
        // get image file by it's filename
        GridFSDBFile imageForOutput = gfsPhoto.findOne(newImage);
        // save it into a new image file
        imageForOutput.writeTo(image);

    }

    public void removeImage(final String fileName)throws Exception{
        GridFS gfsPhoto = new GridFS(database, imageBucket);
        // remove the image file from mongoDB
        gfsPhoto.remove(gfsPhoto.findOne(fileName));
    }
}
