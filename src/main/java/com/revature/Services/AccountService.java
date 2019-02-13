package com.revature.Services;

import java.util.List;
import java.util.Optional;

import com.revature.Dao.AccountDao;
import com.revature.Dao.AccountOracle;
import com.revature.Models.Account;

public class AccountService {

	private static AccountService a;
	private static AccountDao dao = AccountOracle.getDao();
	
	private AccountService() {}
	public static AccountService getService() {
		if(a == null) {
			a = new AccountService();
		}
		
		return a;
	}
	
//	public Optional<List<Account>> getAccounts(int userID){
//		return dao.getAccounts(userID);
//	}
	
	public Optional<List<Account>> getAccounts(int userID){
		return dao.getAccounts(userID);
	}
	
	public Optional<Account> deposit(String accType, double amount, int accID, int userID){
		return  dao.deposit(accType, amount, accID, userID);
	}
	
	public Optional<Account> withdraw(String accType, double amount, int accID, int userID){
		return  dao.withdraw(accType, amount, accID, userID);
	}
	
	public Optional<Account> openAccount(int userID, String accType, double amount){
		return  dao.openAcc(userID, accType, amount);
	}
	
	public Optional<Account> closeAccount(int AccID){
		return  Optional.empty();
	}
}
