package com.example.laowuguanli.net;

import com.example.laowuguanli.ui.activity.MainActivity;

import java.io.IOException;
import java.util.Properties;


//创建连接服务器的类
public class ConnectServer {

    //读取application.properties配置文件
    public static Properties connect() {
        Properties properties = new Properties();
        try {
            properties.load(MainActivity.class.getResourceAsStream("/assets/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    //返回url
    public static String url(){
        return connect().getProperty("url");
    }

}
