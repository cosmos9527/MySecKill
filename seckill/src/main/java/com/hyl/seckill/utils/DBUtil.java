package com.hyl.seckill.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtil {

    private static Properties props;

    static {
        try{
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.properties");
            props = new Properties();
            props.load(in);
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception{
        String url = props.getProperty("spring.datasource.url");
        String driver = props.getProperty("spring.datasource.driver-class-name");
        String username = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password");
        Class.forName(driver);
        return DriverManager.getConnection(url,username,password);
    }
}
