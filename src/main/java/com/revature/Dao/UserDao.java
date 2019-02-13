package com.revature.Dao;

import java.util.Optional;

import com.revature.Models.User;

public interface UserDao {

	Optional<User> login(String username, String password);
	Optional<User> addUser(String firstName, String lastName, String username, String password);
	//getAccounts
		//will need to implement a foreign key into the user table so that I can reference the accounts for each user.
}
