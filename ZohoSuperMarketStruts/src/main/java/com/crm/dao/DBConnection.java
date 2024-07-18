package com.crm.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection{

    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    static{
        try{
            Properties prop = new Properties();

            InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(input);
            url = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");
            driver = prop.getProperty("db.driver");
            Class.forName(driver);
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
            throw new RuntimeException("Failed to load JDBC driver or read database properties");
        }
    }

    public static Connection getConnection() throws Exception{
        Connection con = null;
        con = DriverManager.getConnection(url, username, password);

        return con;
    }

    public static void closeConnection(Connection con) throws Exception{
        if(con != null){
            con.close();
        }
    }
}
