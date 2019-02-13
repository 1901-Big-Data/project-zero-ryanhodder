package com.revature.JDBCBank;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.revature.Models.Account;
import com.revature.Models.User;
import com.revature.Services.AccountService;
import com.revature.Services.UserService;

public class App {
	
	static String uFName;
	static String uLName;
	static String uName;
	static String uPass;
	static int uID;
	static boolean isLoggedIn = false;
	static String mm = new String("\n[MAIN MENU]\nWould you like to\n\t1: Register\n\t2: Login\n\t3: Exit\n\t4: Help");
	
	public static void main(String[] args) {
		
		intro();
		
		try {
			Scanner s = new Scanner(System.in);
			
			while(s.hasNext()) {
				String st = s.nextLine();
				String uIn = st.toLowerCase();

				if(uIn.equals("register") || uIn.equals("1")) {
					register(s);
				}
				if(uIn.equals("login") || uIn.equals("2")) {
					isLoggedIn = login(s);
					
					while(isLoggedIn == true) {
						loggedIn(s);
					}
				}	
				if(uIn.equals("exit") || uIn.equals("3")) {
					System.out.println("Goodbye!");
					break;
				}
				if(uIn.equals("help") || uIn.equals("4")) {
					help();	
				}
			}
			s.close();
		}catch(NoSuchElementException e) {
			System.out.println("Problem with database");
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	
		public static void intro() {
			System.out.println("Hello Sir or Madam\nWelcome to Bank of Revature\nHow are y'all today?\n");
			System.out.println(mm);
		}
		
		public static void register(Scanner input) {
			System.out.println("Please enter your first name:");
			uFName = input.next();					
			System.out.println("Ok, please tell me your last name:");
			uLName = input.next();
			System.out.println("And what should your username be?");
			uName = input.next();
			System.out.println("Finally, set a password please");
			uPass = input.next();			
			
			//need way to check that the username is unique
			
			try {
				User user = UserService.getService().addUser(uFName, uLName, uName, uPass).get();
				System.out.println("OK! The user has been added to the database\nPlease login to continue");
			}catch(NoSuchElementException e) {
				System.out.println("Error adding user to the database\nPlease try again");
			}
			System.out.println(mm);
		}
		
		//might want to return type
		//to check whether it is a super user or not
		public static boolean login(Scanner input) {
			System.out.println("Username:");
			String u = input.next();
			System.out.println("Password:");
			String p = input.next();
			System.out.println("Logging in...");
			
			try {
				User user = UserService.getService().login(u, p).get();
				uID = user.getUserId();
				System.out.println("Hello, " + user.getfName());
				return true;
			}catch(NoSuchElementException e) {
				System.out.println("Sorry, the username/password combination doesn't match.\nPlease try again");
				System.out.println(mm);
				return false;
			}
		}

		
		public static void help() {
			System.out.println("Type 'Register' or '1' to register a user\n"
					+ "Type 'Login' or '2' to login\n"
					+ "Type 'Exit' or '3' to exit\n"
					+ "Type 'Help' or '4' to repeat options\n");
		}
		
		public static void loggedIn(Scanner input) {
			System.out.println("\n[LOGGED IN]\nHow can I help you?\n"
					+ "\t1: Open new account\n"
					+ "\t2: View accounts\n"
					+ "\t3: Deposit $$\n"
					+ "\t4: Withdraw $$\n"
					+ "\t5: Close an account\n"
					+ "\t6: Logout\n");
				//view transaction history
			
			String i = input.next();
			double am = 0.0;
			String ty = "";
			boolean badInfo = false;
			
			if(i.equals("open") || i.equals("1")) {
				System.out.println("What type of account would you like to open?\n\t1: Checking account\n\t2: Savings account");
				i = input.next();
				
				//collecting some initial info for the account
				do {
					if(i.equals("checking")  || i.equals("1")) {
						badInfo = false;
						ty = "Checking";	
					}else if(i.equals("savings") || i.equals("2")) {
						badInfo = false;
						ty = "Savings";
					}else {
						badInfo = true;
						System.out.println("Please make sure you correctly specified which account you would like to open\n"
								+ "type '1' or type 'checking' to open a checking account or type '2' or type 'savings' to open a savings account");
					}
				}while(badInfo);
				
				System.out.println("Ok excellent, and would you like to deposit an initial amount?\n"
						+ "\t1: Yes (y)\n"
						+ "\t2: No (n)\n");
				i = input.next();
				if(i.equals("yes") || i.equals("y") || i.equals("1")) {
					System.out.println("Please enter the amount you would like to deposit.");
					
					do {
						i = input.next();
						badInfo = false;
						am = Double.valueOf(i);
						if(am < 0) {
							badInfo = true;
							System.out.println("Please make sure enter a valid number, greater than zero");
						}
					}while(badInfo);
				}else {
					am = 0.0;
				}
				
				//add new account to the table with the values from user
				try {
					Account a = AccountService.getService().openAccount(uID, ty, am).get();
					
					System.out.println("Ok, one moment while a new account is being generated for you...");
					System.out.println("You can view your accounts in the 'View accounts' section");
				}catch(NoSuchElementException e) {
					System.out.println("Error with the database, please try again later");
				}
				
			}else if(i.equals("view") || i.equals("2")) {
				viewAccounts();

			}else if(i.equals("deposit") || i.equals("3")) {
				viewAccounts();
				System.out.println("Please select the account number you would like to make a deposit into.");
				i = input.next();
				try {
					int accNum = Integer.parseInt(i);
					System.out.println("Ok, and please enter the amount you wish to deposit.");
					do {
						i = input.next();
						badInfo = false;
						am = Double.valueOf(i);
						if(am < 0) {
							badInfo = true;
							System.out.println("Please make sure enter a valid number, greater than zero");
						}
					}while(badInfo);
					Account dAcc = getAccIdAt(accNum);
					dAcc = AccountService.getService().deposit(dAcc.getType(), am, dAcc.getAccId(), dAcc.getUserId()).get();
					System.out.println("Your account has been updated!");
					
				}catch(NoSuchElementException e) {
					System.out.println("Error accessing database. Please try again.");
				}
				
			//make a withdraw
			}else if(i.equals("withdraw") || i.equals("4")) {
				viewAccounts();
				System.out.println("Please select the account number you would like to make a withdrawl from");
				
				i = input.next();
				try {
					int accNum = Integer.parseInt(i);
					System.out.println("Ok, and please enter the amount you wish to withdraw.");
					do {
						i = input.next();
						badInfo = false;
						am = Double.valueOf(i);
						if(am < 0) {
							badInfo = true;
							System.out.println("Please make sure enter a valid number, greater than zero");
						}
					}while(badInfo);
					Account dAcc = getAccIdAt(accNum);
					dAcc = AccountService.getService().withdraw(dAcc.getType(), am, dAcc.getAccId(), dAcc.getUserId()).get();
					System.out.println("Your account has been updated!");
					
				}catch(NoSuchElementException e) {
					System.out.println("Error accessing database. Please try again.");
				}
			//close an account
			}else if(i.equals("close") || i.equals("5")) {
				viewAccounts();
				System.out.println("Please select the account you would like to close");
				i = input.next();
				
				try {
					int accNum = Integer.parseInt(i);
					Account dAcc = getAccIdAt(accNum);
					boolean b = AccountService.getService().closeAccount(dAcc.getAccId(), dAcc.getAmount());
					if(b) {
						System.out.println("You have successfully closed the account");
					}
					else {throw new SQLException(); }
					
				}catch(SQLException e) {
					System.out.println("Please make sure the account you are trying to close does not have any money in it.");
				}
				
			}else if(i.equals("logout") || i.equals("6")) {
				System.out.println("Logging out...\n");
				System.out.println(mm);
				isLoggedIn = false;
			}
		}
		
		public static void viewAccounts() {
			try {
				int count = 1;
				List<Account> listOfAccs = AccountService.getService().getAccounts(uID).get();
				for(Account a: listOfAccs) {
					System.out.println("| Account Number: " + count + " | Account Type: " + a.getType() + " | Balance: " + Double.toString(a.getAmount()));
					count++;
				}
			}
			catch(NoSuchElementException e) {
				System.out.println("You do not have any open accounts.");
			}
		}
		
		public static Account getAccIdAt(int pos) {
			try {
				int count = 1;
				Account returnAcc = new Account();
				List<Account> listOfAccs = AccountService.getService().getAccounts(uID).get();
				for(Account a: listOfAccs) {
					if(pos == count) {
						returnAcc = a;
						return returnAcc;
					}
					count++;
				}
				return returnAcc;
			}
			catch(NoSuchElementException e) {
				System.out.println("Error retreiving account information");
				return null;
			}
			
		}
}
