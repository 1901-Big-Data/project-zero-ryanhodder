package com.revature.JDBCBank;

import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.revature.Models.User;
import com.revature.Services.UserService;

public class App {
	
	static String uFName;
	static String uLName;
	static String uName;
	static String uPass;
	static boolean isLoggedIn = false;
	static String mm = new String("\n[MAIN MENU]\nWould you like to\n\t1: Register\n\t2: Login\n\t3: Exit\n\t4: Help");
	
	public static void main(String[] args) {
		
		
		//trigger in sql will create the USER_ID and ACCOUNT_ID
		
		//UserService.getService().addUser(username, password).get();
		//-->get because Optional
		//--> have to put in trycatch because it is an optional
		//have to check for NoSuchElementException because it is an optional
		//we dont rn but could do throws exception incase we want to check password is correct format or something
		
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
				System.out.println("Hello, " + user.getfName());
				return true;
			}catch(NoSuchElementException e) {
				System.out.println("Sorry, the username/password combination doesn't match.\nPlease try again");
				System.out.println(mm);
				return false;
			}
			
			//make sure it matches the corresponding username
			//else "Username or password does not match"
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
			
			if(i.equals("open") || i.equals("1")) {
				System.out.println("What type of account would you like to open?\n\t1: Checking account\n\t2: Savings account");
				i = input.next();
				if(i.equals("checking")  || i.equals("1")) {
					System.out.println("Ok, one moment while a new checking account is being generated for you...");
					
				}else if(i.equals("savings") || i.equals("2")) {
					System.out.println("Ok, one moment while a new savings account is being generated for you...");
				}else {
					System.out.println("Please make sure you correctly specified which account you would like to open\n"
							+ "type '1' or type 'checking' to open a checking account or type '2' or type 'savings' to open a savings account");
				}
			}else if(i.equals("view") || i.equals("2")) {
				//get acc info here
				System.out.println("View your accounts");
			}else if(i.equals("deposit") || i.equals("3")) {
				//deposit that shit
				System.out.println("Deposit money into an account");
			}else if(i.equals("withdraw") || i.equals("4")) {
				//get that bread
				System.out.println("Withdraw $$");
			}else if(i.equals("close") || i.equals("5")) {
				//bye bye account
				System.out.println("Close an account");
			}else if(i.equals("logout") || i.equals("6")) {
				System.out.println("Logging out...\n");
				System.out.println(mm);
				isLoggedIn = false;
			}
					
		}
}
