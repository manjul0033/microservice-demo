package com.example.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ManageWorkoutIdController {
	
	public Connection getConnection() {
		
		Connection conn = null;
		
		String url = "postgres://<url>";

		url ="jdbc:postgresql://<url>";
		Properties props = new Properties();
		props.setProperty("user","");
		props.setProperty("password","");
		props.setProperty("ssl","true");
		try {
			Class.forName("org.postgresql.Driver"); 
			conn = DriverManager.getConnection(url, props);
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return conn;
	}
		
	@RequestMapping("/Id")
	public @ResponseBody String getWorkoutId(){

		String workout ="";
		Connection conn = getConnection();
			try {
				 PreparedStatement ps = conn.prepareStatement("select workout from workout");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					workout = rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("connection is not closed in update id");
				}				
			}
		System.out.println("\n Data from Database : " +workout+ "\n");
		return workout;
	} 
	
	@RequestMapping("/updateId")
	public @ResponseBody String updateWorkoutId(@RequestParam String Id){

		String workout ="";
		Connection conn = getConnection();
			 Statement stmt;
			try {
				stmt = conn.createStatement();

//				String sql1 = "DROP TABLE workout "; 
//			    stmt.executeUpdate(sql1); 
//			    String sql = "CREATE TABLE workout (workout VARCHAR(255) not NULL)"; 
//			    stmt.executeUpdate(sql);
			    
				stmt.executeUpdate("insert into workout values("+Id+")");
				PreparedStatement ps = conn.prepareStatement("select * from workout");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					workout = rs.getString(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("connection is not closed in update id");
				}				
			}
		System.out.println("\n Data from Database : " +workout+ "\n");
		return workout;
	}

}
