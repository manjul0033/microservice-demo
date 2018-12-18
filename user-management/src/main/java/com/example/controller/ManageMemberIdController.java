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
public class ManageMemberIdController {
	
	public Connection getConnection() {
		
		Connection conn = null;
		
		String url = "postgres://hrlhmrtu:0ADjEQq_NSVLXdTddG0KMjL-pKr0cFo9@echo.db.elephantsql.com:5432/hrlhmrtu";

		url ="jdbc:postgresql://echo.db.elephantsql.com:5432/hrlhmrtu";
		Properties props = new Properties();
		props.setProperty("user","hrlhmrtu");
		props.setProperty("password","0ADjEQq_NSVLXdTddG0KMjL-pKr0cFo9");
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
	public @ResponseBody String getMemberId(){

		String person ="";
		Connection conn = getConnection();
			try {
				 PreparedStatement ps = conn.prepareStatement("select * from person");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					person = rs.getString(1);
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
		System.out.println("\n Data from Database : " +person+ "\n");
		return person;
	} 
	
	@RequestMapping("/updateId")
	public @ResponseBody String updateMemberId(@RequestParam String Id){

		String person ="";
		Connection conn = getConnection();
			 Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("insert into person values("+Id+")");
				PreparedStatement ps = conn.prepareStatement("select * from person");
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					person = rs.getString(1);
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
		System.out.println("\n Data from Database : " +person+ "\n");
		return person;
	}

}
