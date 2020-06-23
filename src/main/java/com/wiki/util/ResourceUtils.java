package com.wiki.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceUtils {

    private static ResourceUtils resourceUtils = new ResourceUtils( );
    private static Properties resourceProperties = new Properties();

    private ResourceUtils() {
        ClassLoader classLoader = getClass().getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream("application.properties")) {
            resourceProperties.load(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Properties getProperties( ) {
        return resourceProperties;
    }


    public String getValue(String key){
        return resourceProperties.getProperty(key);
    }
}
