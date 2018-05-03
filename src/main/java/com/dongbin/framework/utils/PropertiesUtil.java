package com.dongbin.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dongbin on 2018/4/27.
 */
public class PropertiesUtil {

    public static Properties loadProps(String filename) {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = ClassUtil.getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                throw new Exception("tools.properties 不存在");
            }
            properties.load(inputStream);
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
            return properties;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
