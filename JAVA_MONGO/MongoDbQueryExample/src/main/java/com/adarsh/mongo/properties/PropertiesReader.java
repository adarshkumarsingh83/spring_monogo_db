/*
 * Copyright © MeritTrac Services Pvt. Ltd.
 * All Rights Reserved.
 */

package com.adarsh.mongo.properties;
/*
 * {@inheritDoc}
*/

/**
 * @Product : PropertiesReader provide implementation of the specification
 *                    provided for the reading the properties from the
 *                    property file.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author $CreatedBy Adarsh_K
 * @author $LastChangedBy: Adarsh_K
 * @version $Revision: 1.0 $, $Date:: 3/6/13 11:38 AM
 * @see
 */

public class PropertiesReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);
    private static final Properties PROPERTIES = new Properties();


    static {
        try {
            setProperties();
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
    }

    private static final File getPropertyFile() {
        try {
            File propertyFile = new File("src\\main\\resources\\application.xml");
            if (!propertyFile.canRead()) {
                throw new RuntimeException(propertyFile.getName() + " can't read ");
            }
            return propertyFile;
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    private static final FileInputStream getFileInputStream(final File fileObject) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileObject);
            return fileInputStream;
        } catch (Exception e) {
            LOGGER.error(e.toString());
        }
        return null;
    }

    private static final void setProperties() throws Exception {
        File file = getPropertyFile();
        FileInputStream fileInputStream = getFileInputStream(file);
        PROPERTIES.loadFromXML(fileInputStream);
    }

    public static final String getProperty(final String key) {
        if (PROPERTIES.containsKey(key)) {
            return PROPERTIES.get(key) + "";
        }
        return null;
    }
}
