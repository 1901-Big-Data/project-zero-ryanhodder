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
			String sql = "select * from bankCustomers where user_name = ? and user_pass = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			//callable statement is only for procedures
			
			System.out.println(rs.getFetchSize());
			
			//Maybe want to change User model so that user_id is an int
			User user = new User();
			
			//using boolean to check if the result set is empty
			boolean rsempty = true;
			if(rs.next()) {
				rsempty = false;
				user = new User(rs.getString("first_name"), rs.getString("last_name"), username, password, rs.getString("USER_ID"));
			}
			if(rsempty) {
				return Optional.empty();
			}
			return Optional.of(user);
			
		}catch(SQLException e) {
			l.error(e.getMessage());
			return Optional.empty();
		}
	}

	@Override
	public Optional<User> addUser(String firstName, String lastName, String username, String password) {
				l.traceEntry();
				
				Connection c = ConnectionUtil.getConnection();
				
				if(c == null) {
					l.traceExit(Optional.empty());
					return Optional.empty();
				}
				
				try {
					String sql = ("insert into bankCustomers values(?,?,?,?,?)");
					PreparedStatement ps = c.prepareStatement(sql);
					ps.setInt(1, 2);
					ps.setString(2, firstName);
					ps.setString(3, lastName);
					ps.setString(4, username);
					ps.setString(5, password);
					int result = ps.executeUpdate();
					
					
					User user = new User();
					//means 1 row was updated
					if(result == 1) {
						user = new User("", firstName, lastName, username, "");
					}else {
						return Optional.empty();
					}
					return Optional.of(user);
					
				}catch(SQLException e) {
					l.error(e.getMessage());
					return Optional.empty();
				}
	}
		
}
