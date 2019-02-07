package com.revature.JDBCBank;

public class User {
	
	private String fName;
	private String lName;
	private String userName;
	private String pass;
	private int numOfAccs = 0;
	private int numOfUsers = 0;
	private final int USER_ID;
	
	//hasAccounts
	//getAccounts []
	
	//createAcc
	//need to create an account id so that all accounts have a unique identifier
	//would you like to deposit an initial amount?
	
	//delete account
	//make sure it is empty
	//maybe want a confirmation from the user
	//wait for Yes or no Y/N

	public User(String firstName, String lastName, String Username, String Password) {
		fName = firstName;
		lName = lastName;
		userName = Username;
		pass = Password;
		numOfAccs = 0;
		USER_ID = generateID();
	}
	
	//generate an ID for the user
	private int generateID() {
		return numOfUsers++;
	}
	
	/**
	 * //check sql to see if this user has created an account
	 * @return
	 */
	public boolean hasAccount() {
		if(numOfAccs == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 */
	public void getAccounts() {
		
	}
	
	
	private void createAcc() {
		//add new account to sql
		
		System.out.println("You have successfully created a new account");
	}
}
