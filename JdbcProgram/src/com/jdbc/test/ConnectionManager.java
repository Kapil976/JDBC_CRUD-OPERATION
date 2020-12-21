package com.jdbc.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	 private static String url = "jdbc:oracle:thin:@192.168.100.173:1521:ORA10G";    
	    private static String driverName = "oracle.jdbc.driver.OracleDriver";   
	    private static String username = "TRAINERP";   
	    private static String password = "TRAINERP";
	    private static Connection con;

	    public static Connection getConnection() {
	        try {
	            Class.forName(driverName);
	            try {
	            	con = DriverManager.getConnection(url, username, password);
					System.out.println("Connection Establish");
	            } catch (SQLException ex) {
	                System.out.println("Failed to create the database connection."); 
	            }
	        } catch (ClassNotFoundException ex) {
	            System.out.println("Driver not found."); 
	        }
	        return con;
	    }
}
