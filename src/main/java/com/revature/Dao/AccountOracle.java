package com.revature.Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.Models.Account;
import com.revature.Util.ConnectionUtil;

public class AccountOracle implements AccountDao{

	private static AccountOracle a;
	private static final Logger l = LogManager.getLogger(AccountOracle.class);
	
	private AccountOracle() {}
	public static AccountDao getDao() {
		if(a == null) {
			a = new AccountOracle();
		}
		
		return a;
	}
	
	@Override
	public Optional<Account> deposit(String accType, double amount, int accID, int userID) {
		l.traceEntry();
		
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			l.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "call deposit(?,?,?)";
			CallableStatement cs = c.prepareCall(sql);
			cs.setInt(1, accID);
			cs.setDouble(2, amount);
			cs.registerOutParameter(3, Types.DOUBLE);
			cs.execute();
			
			double newBal = cs.getDouble(3);
			
			Account a = new Account(accType, newBal, accID, userID);
			return Optional.of(a);
			
		}catch(SQLException e) {
			l.catching(e);
			return Optional.empty();
		}
	}
	@Override
	public Optional<Account> withdraw(String accType, double amount, int AccID, int userID) {
		l.traceEntry();
		
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			l.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "Select balance from bankaccounts WHERE BANK_ACCOUNT_ID = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, AccID);
			ResultSet rs = ps.executeQuery();
			boolean b = rs.next();
			
			double currAmount = rs.getDouble("balance");
			System.out.println(currAmount);
			
			if((currAmount - amount) < 0) {
				System.out.println("You cannot withdraw more money than you have in the account");
				System.out.println("Please try again");
				return Optional.empty();
			}else {
				sql = "call withdraw(?,?,?)";
				CallableStatement cs = c.prepareCall(sql);
				cs.setInt(1, AccID);
				cs.setDouble(2, amount);
				cs.registerOutParameter(3, Types.DOUBLE);
				
				cs.execute();
				
				double newBal = cs.getDouble(3);
				
				Account a = new Account(accType, newBal, AccID, userID);
				
				return Optional.of(a);
			}
		}catch(SQLException e) {
			l.catching(e);
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<Account> openAcc(int userID, String accType, double amount) {
		l.traceEntry();
		
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			l.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "call addAccount(?,?,?,?)";
			CallableStatement cs = c.prepareCall(sql);
			cs.setString(1, accType);
			cs.setDouble(2, amount);
			cs.setInt(3, userID);
			cs.registerOutParameter(4, Types.INTEGER);
			
			cs.execute();
			
			int accountID = cs.getInt(4);
			
			Account a = new Account(accType, amount, accountID, userID);
			return Optional.of(a);			
			
		}catch(SQLException e) {
			l.catching(e);
			return Optional.empty();
		}
	}
	
	//Returns true if delete was successful
	@Override
	public boolean closeAcc(int AccID, double amount) {
		l.traceEntry();
		
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			l.traceExit(false);
			return false;
		}
		
		if(amount != 0.0) {
			return false;
		}else {
			try {
				String sql = "DELETE FROM bankAccounts WHERE BANK_ACCOUNT_ID = ?";
				PreparedStatement ps = c.prepareStatement(sql);
				ps.setInt(1, AccID);
				ResultSet rs = ps.executeQuery();
				
				return true;
				
			}catch(SQLException e) {
				l.catching(e);
				return false;
			}
			
		}
	}
	
	@Override
	public Optional<List<Account>> getAccounts(int userID) {
		//can get a result set 
		//to get a list of the accounts for each user
		l.traceEntry();
		
		Connection c = ConnectionUtil.getConnection();
		
		if(c == null) {
			l.traceExit(Optional.empty());
			return Optional.empty();
		}
		
		try {
			String sql = "Select * from bankAccounts where USER_ID = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, userID);
			ResultSet accResults = ps.executeQuery();
			
			List<Account> accList = new ArrayList<Account>();
			
			l.trace("Have completed the query");
			
			boolean rsEmpty = true;
			while(accResults.next()) {
				rsEmpty = false;
				accList.add(new Account(accResults.getString("acc_type"), accResults.getDouble("balance"), accResults.getInt("BANK_ACCOUNT_ID"), userID));
			}
			
			if(rsEmpty) {
				return Optional.empty();
			}
			
			return Optional.of(accList);
			
			
		}catch(SQLException e) {
			l.catching(e);
			return Optional.empty();
		}
	}
	
	
}
