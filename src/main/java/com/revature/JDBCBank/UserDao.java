package com.revature.JDBCBank;

import java.util.Optional;

public interface UserDao {
	
	Optional<User> getUserByUsername(String Username);
	
}
