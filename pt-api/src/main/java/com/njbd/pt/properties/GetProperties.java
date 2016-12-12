package com.njbd.pt.properties;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by 李建成
 * ON:  2016/9/17 13:44
 */
public class GetProperties {

    public static String uid = null;

    public static String key = null;

    public static String isPublish = null;


    /**
     * uid
     * @return
     * @throws IOException
     */
    public static String getSendUid() throws IOException {
        if (uid != null) {
            return uid;
        }
        Properties prop = new Properties();// 属性集合对象
        //定位看到包路径的第一级路径
        InputStream fis = GetProperties.class.getClassLoader().getResourceAsStream("configs/url.properties");
        prop.load(fis);// 将属性文件流装载到Properties对象中
        fis.close();// 关闭流
        String url = prop.getProperty("Uid");
        uid = url;
        return url;
    }

    /**
     * 短信的key
     * @return
     * @throws IOException
     */
    public static String getSendKey() throws IOException {
        if (key != null) {
            return key;
        }
        Properties prop = new Properties();// 属性集合对象
        InputStream fis = GetProperties.class.getClassLoader().getResourceAsStream("configs/url.properties");
        prop.load(fis);// 将属性文件流装载到Properties对象中
        fis.close();// 关闭流
        String url = prop.getProperty("Key");
        key = url;
        return url;
    }


    /**
     * 服务器是否发布中
     * @return
     * @throws IOException
     */
    public static String getIsPublish() throws IOException {
        if (isPublish != null) {
            return isPublish;
        }
        Properties prop = new Properties();// 属性集合对象
        InputStream fis = GetProperties.class.getClassLoader().getResourceAsStream("configs/url.properties");
        prop.load(fis);// 将属性文件流装载到Properties对象中
        fis.close();// 关闭流
        String url = prop.getProperty("IsPublish");
        isPublish = url;
        return url;
    }


}
