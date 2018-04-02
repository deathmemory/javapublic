package com.dm.util;

import java.sql.*;

public class SQLiteJDBC {
	private static String databaseName = "paylog.db";
	private static Connection c = null;
	
	public static  Boolean init() {
		return SQLiteJDBC.connect();
	}
	
	public static Connection getConnection() {
		return c;
	}
	
	public static Boolean connect() {
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
//	      c.setAutoCommit(false);
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      return false;
	    }
	    System.out.println("Opened database successfully");
	    return true;
	}
	
	public static void close() {
		if (c != null) {
			try {
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Boolean createTable(String sql) {
		return executeSQL(sql);
	}
	
	public static Boolean createTable() {
		String sql = "CREATE TABLE paylog " +
                "(id INTEGER PRIMARY KEY         AUTOINCREMENT," +
                " name           CHAR(50)    NOT NULL, " + 
                " params         CHAR(500)   NOT NULL, " +
                " status 		  INT," +
                " time           DATETIME    NOT NULL)"; 
		return createTable(sql);
	}
	
	public static Boolean executeSQL(String sql) {
		try {
			if (getConnection() == null) {
				init();
			}
			
			Statement stmt = c.createStatement();
			
			stmt.executeUpdate(sql);
			stmt.close();
//			c.commit();
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("SQLException -> " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public static Boolean insert(String name, String params, int status) {
		String sql = "INSERT INTO paylog (name,params,status,time) " +
                "VALUES ('" + name + "', '" + params + "', '" + status + "', datetime('now', 'localtime'));";
		return executeSQL(sql);
	}
	
	public static void selectTest() {
		ResultSet rs = null;
		try {
			Statement stmt = c.createStatement();
			rs = stmt.executeQuery( "SELECT * FROM paylog;" );
//			int rowCount = resultSet.getInt("rowCount");
		      while ( rs.next() ) {
		         int id = rs.getInt("id");
		         String  name = rs.getString("name");
		         String params  = rs.getString("params");
		         int status = rs.getInt("status");
		         String time  = rs.getString("time");
		         System.out.println( "ID = " + id );
		         System.out.println( "name = " + name );
		         System.out.println( "params =" + params );
		         System.out.println( "status = " + status );
		         System.out.println( "time = " + time );
		         System.out.println();
		      }
		      rs.close();
		      stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
