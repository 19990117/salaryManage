package com.ten.aspect;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.logging.log4j.core.appender.db.jdbc.ConnectionSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class Connect  {
    @Autowired
    private DataSource mysqlDataSource;

    public Connection getConnection() throws SQLException {
        return mysqlDataSource.getConnection();
    }
}
