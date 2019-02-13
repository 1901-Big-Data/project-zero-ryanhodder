package com.revature.Dao;

import java.util.List;
import java.util.Optional;

import com.revature.Models.Account;

public interface AccountDao {
	
	Optional<List<Account>> getAccounts(int userID);
	Optional<Account> deposit(String accType, double amount, int accID, int userID);
	Optional<Account> withdraw(String accType, double amount, int accID, int userID);
	Optional<Account> openAcc(int userID, String accType, double amount);
	Optional<Account> closeAcc(int AccID);
}
