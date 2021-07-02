/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo;
/*
 * {@inheritDoc}
*/

/**
 * @Product : ImageTest provide implementation of the specification
 *                    provided for the ...    
 */


import com.adarsh.mongo.repositores.ImageRepository;
import com.adarsh.mongo.repositores.ImageRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 18/6/13 9:27 AM
 * @see
 */
public class ImageTest {

    private static final Logger LOGGER= LoggerFactory.getLogger(ImageTest.class);
    private static final ImageRepository IMAGE_REPOSITORY=new ImageRepositoryImpl();

    @Test
    public void imageOperation()throws Exception{
        final String imageNew="adarsh-kumar-singh";
        IMAGE_REPOSITORY.saveImage("src\\main\\resources\\upload\\Home.png",imageNew);
        IMAGE_REPOSITORY.displayImages();
        IMAGE_REPOSITORY.getImages("src\\main\\resources\\download\\HomeNew.png",imageNew);
        IMAGE_REPOSITORY.removeImage(imageNew);
    }
}
