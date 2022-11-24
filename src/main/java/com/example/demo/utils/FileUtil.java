package com.example.demo.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class FileUtil {

    @Autowired
    static ResourceLoader resourceLoader;

    public static String readFileFromClasspath(String pathOnClasspath) {
        try (InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(pathOnClasspath)) {
            if (is == null) {
                throw new IllegalStateException("can't load file: " + pathOnClasspath);
            }

            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

            return buffer.lines().collect(Collectors.joining("\n"));

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}