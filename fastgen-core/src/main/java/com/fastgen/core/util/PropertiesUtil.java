package com.fastgen.core.util;

import java.io.*;
import java.util.Properties;

/**
 * Properties解析工具类
 *
 * @author: linzetian
 * @date:2019/9/20
 */
public class PropertiesUtil {

    /**
     * 将内容转成Properties
     *
     * @param content
     * @return
     */
    public static Properties contentToProperties(String content) {
        Properties properties = new Properties();
        try {
            InputStream is = new ByteArrayInputStream(content.getBytes());
            properties.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
