package com.adarsh.monog;

import com.adarsh.mongo.core.GifFileReaderMonogDb;
import com.adarsh.mongo.core.GifFileWriterMonogDb;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Adarsh_K
 * Date: 1/5/13
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    @org.junit.Test
    public void store() throws Exception{
        File fileObject = new File("C:\\imagetest\\upload\\test.png");
        GifFileWriterMonogDb gifFileWriterMonogDb = new GifFileWriterMonogDb();
        System.out.println("Store Operation :-> " + gifFileWriterMonogDb.storeInDb(fileObject));
    }

    @org.junit.Test
    public void read() {
        GifFileReaderMonogDb gifFileReaderMonogDb = new GifFileReaderMonogDb();
        File fileObject = new File("C:\\imagetest\\download\\test.png");
        System.out.println("Read opeation :-> " + gifFileReaderMonogDb.read(fileObject));
    }


}
