package com.revature.Dao;

import java.util.Optional;

import com.revature.Models.User;

public interface UserDao {

	Optional<User> login(String username, String password);
	Optional<User> addUser(String firstName, String lastName, String username, String password);
}
