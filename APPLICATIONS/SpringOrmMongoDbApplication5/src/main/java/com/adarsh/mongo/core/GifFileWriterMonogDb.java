package com.adarsh.mongo.core;

import java.io.*;
import java.nio.file.Files;

import com.adarsh.mongo.config.MongoConfigFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.activation.MimetypesFileTypeMap;


public class GifFileWriterMonogDb {

    private static final ApplicationContext ctx = new AnnotationConfigApplicationContext(MongoConfigFactory.class);

    public boolean storeInDb(final File fileObject) throws Exception {
        boolean result = false;

        GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
        InputStream inputStream = null;
        try {
            if (!fileObject.exists()) {
                throw new FileNotFoundException("File Not Found " + fileObject.getAbsolutePath());
            }
            inputStream = new FileInputStream(fileObject);
            DBObject metaData = new BasicDBObject();
            metaData.put("filename", fileObject.getName());
            final String mimeType = Files.probeContentType(fileObject.toPath());
            gridOperations.store(inputStream, fileObject.getName(), mimeType, metaData);

            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Done");
        return result;
    }
}
