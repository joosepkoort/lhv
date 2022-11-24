package com.example.demo.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyLoader {

    private static final String PROPERTIES_FILENAME = "application.properties";

    private static Properties cache;


    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }

    public static Properties getProperties() {
        if (cache != null) {
            return cache;
        }

        Properties properties = new Properties();

        try {
            String contents = FileUtil.readFileFromClasspath(PROPERTIES_FILENAME);
            properties.load(new ByteArrayInputStream(contents.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cache = properties;

        return properties;
    }
}