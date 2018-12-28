package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
		
		static {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static Connection getConnection() throws SQLException {
			String url = System.getProperty("db_url");
			String port = System.getProperty("db_port");
			String dbName = System.getProperty("db_name");
			String dbSchema = System.getProperty("db_schema");
			String username = System.getProperty("db_username");
			String password = System.getProperty("db_password");

			String dataSource = "jdbc:postgresql://" + url + ":" + port + "/" + dbName + "?currentSchema=" + dbSchema;

			return DriverManager.getConnection(dataSource, username, password);
		}
		
		
		
		
}
