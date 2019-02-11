package com.revature.Services;

import java.util.Optional;

import com.revature.Dao.UserDao;
import com.revature.Dao.UserOracle;
import com.revature.Models.User;

public class UserService {
	
	private static UserService s;
	private static UserDao dao = UserOracle.getDao();
	
	private UserService() {
		
	}
	
	public static UserService getService() {
		if(s == null) {
			s = new UserService();
		}
		
		return s;
	}
	
	//industry grade = authorization object ot tell if they are admin or not
	/**
	 * Returns that User object if successfully logged in
	 * 
	 * Exception will be thrown for incorrect login info
	 * Empty optional is for connection error
	 */
	public Optional<User> login(String username, String password){
		return dao.login(username, password);
	}

	
	public Optional<User> addUser(String firstName, String lastName, String username, String password) {
		return dao.addUser(firstName, lastName, username, password);
	}
}
