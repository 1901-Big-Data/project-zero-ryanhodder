package com.revature.JDBCBank;

import java.util.Optional;

public class UserService implements UserDao{

	public Optional<User> getUserByUsername(String Username) {
		// TODO Auto-generated method stub
		return null;
	}
	//login
	//deposit
	//withdraw
	
	//UserDao d = new UserOracle;
	
	private static UserService s;
	private UserService() {
		
	}
	
	public static UserService getService() {
		if(s == null) {
			s = new UserService();
		}
		
		return s;
	}
	
	//he does this in Oracle
	//industry grade = authorization object ot tell if they are admin or not
	//login
	public Optional<Boolean> login() throws Exception{
		//throws exception if incorrect pass
		//optional network error
		//boolean if admin or not
		return null;
	}
	
	
	//dao and service going to be v similar on basic products
}
