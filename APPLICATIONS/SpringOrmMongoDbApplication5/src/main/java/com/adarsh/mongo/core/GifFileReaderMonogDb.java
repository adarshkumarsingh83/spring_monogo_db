package com.adarsh.mongo.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;

import com.mongodb.gridfs.GridFSDBFile;


public class GifFileReaderMonogDb {

    private static final ApplicationContext ctx = new GenericXmlApplicationContext("classpath:/spring/applicationContext.xml");

    public boolean read(final File fileObject) {
        boolean operationResult = false;
        GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

        List<GridFSDBFile> result = gridOperations.find(
                new Query().addCriteria(Criteria.where("filename").is(fileObject.getName())));
        for (GridFSDBFile file : result) {
            try {
                System.out.println(file.getFilename());
                System.out.println(file.getContentType());

                //save as another image
                file.writeTo(fileObject.getAbsolutePath());
                operationResult = true;
            } catch (IOException e) {
                e.printStackTrace();
                operationResult = false;
            }
        }
        System.out.println("Done");
        return operationResult;
    }

}
