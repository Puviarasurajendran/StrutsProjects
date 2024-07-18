package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnection {

	private static String url ="jdbc:mysql://mysqldb-container:3306/sys";
//	private static String url ="jdbc:mysql://localhost:3306/sys";
	private static String username="root";
	private static String password="password";
	private static String driver="com.mysql.cj.jdbc.Driver";

    static {
        try {
        	System.out.println("Inside customer  fetch api db connection ");
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load JDBC driver");
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        try {
            // Get a connection to the database
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

	public static void closeConnection(Connection con) {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}




