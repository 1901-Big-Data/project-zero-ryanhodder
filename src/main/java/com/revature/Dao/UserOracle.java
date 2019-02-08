package com.revature.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.Models.User;
import com.revature.Util.ConnectionUtil;

public class UserOracle implements UserDao {

	private static UserOracle u;
	private static final Logger l = LogManager.getLogger(UserOracle.class);
	
	//singleton
	private UserOracle() {
		
	}
	
	public static UserDao getDao() {
		if(u == null) {
			u = new UserOracle();
		}
			
		return u;
	}

	@Override
	public Optional<User> login(String username, String password) {
		//log entry at beginning of method
		l.traceEntry();
		
		//Connection in ConnectionUtil is static so I don't need to make a new class
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			//exit method
			//if c was null then there was no way to get a User
			//so we return an empty optional
			l.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			//Sql query here
			String sql = "select * from testTable where test_uname = ? and test_pass = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			System.out.println(rs.getFetchSize());
			
			//Maybe want to change User model so that user_id is an int
			User u = new User(rs.getString("test_fname"), rs.getString("test_lname"), username, password, rs.getString("test_id"));
			
			return Optional.of(u);
			
		}catch(SQLException e) {
			l.error(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> addUser(String firstName, String lastName, String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
