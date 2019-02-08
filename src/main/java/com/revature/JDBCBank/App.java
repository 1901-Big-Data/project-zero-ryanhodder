package com.revature.JDBCBank;

import java.util.Scanner;

public class App {
	
	static String uFName;
	static String uLName;
	static String uName;
	static String uPass;
	static boolean isLoggedIn = false;
	
	public static void main(String[] args) {
		
		//want to make sure to parse user input
		//basically want to make sure of caps, so I can match words against theirs 
		//is there a situation where I will want to retain the user wrote their input?
		//yes for name and password purposes, especially the password could be case sensitive
		
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
					login(s);
					isLoggedIn = true;
					while(isLoggedIn == true) {
						loggedIn(s);
					}
					//give access to new menu where user can do things
				}	
				if(uIn.equals("exit") || uIn.equals("3")) {
					break;
				}
				if(uIn.equals("help") || uIn.equals("4")) {
					help();	
				}
			}
			s.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
		
		//user dao
		//implements calls that service will use
		//UserOracle implements userdao
		//UserService 
	}
	
		public static void intro() {
			System.out.println("Hello Sir or Madam\nWelcome to Bank of Revature\nHow are y'all today?\n");
			System.out.println("Would you like to\n\t1: Register\n\t2: Login\n\t3: Exit\n\t4: Help");
		}
		
		
		//could give these methods a scanner input
		public static void register(Scanner input) {
			System.out.println("Please enter your first name:");
			uFName = input.next();					
			System.out.println("Ok, please tell me your last name:");
			uLName = input.next();
			System.out.println("And what should your username be?");
			uName = input.next();
			System.out.println("Finally, set a password please");
			uPass = input.next();			
			
			//is this where I want to use the Prepared string
			
			//Just creating a user here but would put into sql at this point
			//User thisDude = new User(uFName, uLName, uName, uPass);
			System.out.println("Alrighty boss you're all signed up\nLogin to continue\n");
		}
		
		//might want to return type
		//to check whether it is a super user or not
		public static void login(Scanner input) {
			System.out.println("Username:");
			String u = input.next();
			System.out.println("Password:");
			String p = input.next();
			System.out.println("Logging in...");
			//make sure it matches the corresponding username
			
			//if() allgravy the maybe have a bool for loggedIn = true
			//can maybe do that in the db
			//will change menus accordingly
		}

		
		public static void help() {
			System.out.println("Type 'Register' or '1' to register a user\n"
					+ "Type 'Login' or '2' to login\n"
					+ "Type 'Exit' or '3' to exit\n"
					+ "Type 'Help' or '4' for a repeat\n");
		}
		
		public static void loggedIn(Scanner input) {
			System.out.println("What is up my guy\n"
					+ "\t1: Create Account\n"
					+ "\t2: View Accounts\n"
					+ "\t3: Deposit $$\n"
					+ "\t4: Withdraw $$\n"
					+ "\t5: Delete Account\n"
					+ "\t6: Logout\n");
				//view transaction history
			
			String i = input.next();
			
			if(i.equals("create") || i.equals("1")) {
				System.out.println("Creating account...");
			}
			if(i.equals("view") || i.equals("2")) {
				System.out.println("Going to your accounts");
			}
			if(i.equals("deposit") || i.equals("3")) {
				System.out.println("Depositing...");
			}
			if(i.equals("withdraw") || i.equals("4")) {
				System.out.println("W");
			}
			if(i.equals("delete") || i.equals("5")) {
				System.out.println("D");
			}
			if(i.equals("logout") || i.equals("6")) {
				System.out.println("Logging out...");
				isLoggedIn = false;
			}
					
		}
}
