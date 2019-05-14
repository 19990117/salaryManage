package com.ten.aspect;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {



    private DruidDataSource dataSource;

    private  static ConnectionFactory connectionFactory;

    private Connection getConnection() throws SQLException {

        Properties properties = new Properties();
        String user = "root";
        String password = "root";
//        String url = "jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String url = "jdbc:mysql://47.106.153.21:3306/salary?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        properties.put("driverClassName",driverClassName);
        properties.put("url",url);
        properties.put("username",user);
        properties.put("password",password);

        try {
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            try {
            } catch (Exception e2) {
            }
        }
        System.err.println("实例化");
        return  dataSource.getConnection();
    }

    public static Connection getDatabaseConnection() throws SQLException {
        if(connectionFactory==null){
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory.getConnection();

    }



}
