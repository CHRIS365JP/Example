package example.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONReader;

public class CalendarUtils {
    
    private static String HOLIDAY_CONFIG_FILE = "Holiday.json";
    
    private static String ERA_CONFIG_FILE = "Era.json";
    
    public static String getEra(String fileName) {
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String str = "";
        JSONReader reader = null;
        try {
            if(StringUtils.isEmpty(fileName)) {
                fileName = ERA_CONFIG_FILE;
            }
            reader = new JSONReader(new FileReader(new File(classpath + fileName)));
            str = reader.readString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        return str;
    }
    
    public static String getHoliday(String fileName) {
        String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String str = "";
        JSONReader reader = null;
        try {
            if(StringUtils.isEmpty(fileName)) {
                fileName = HOLIDAY_CONFIG_FILE;
            }
            reader = new JSONReader(new FileReader(new File(classpath + fileName)));
            str = reader.readString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

        return str;
    }
}
