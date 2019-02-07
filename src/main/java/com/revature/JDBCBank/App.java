package com.revature.JDBCBank;

import java.util.Scanner;

public class App {
	
	//user commands
	static String create = new String("create");
	static String delete = new String("delete");
	static String deposit = new String("deposit");
	static String withdraw = new String("withdraw");
	static String view = new String("view");
	static String register = new String("register");
	static String login = new String("login");
	
	public static void main(String[] args) {
		
		//want to make sure to parse user input
		//basically want to make sure of caps, so I can match words against theirs 
		//is there a situation where I will want to retain the user wrote their input?
		//yes for name and password purposes, especially the password could be case sensitive
		
//		System.out.println("Hello Sir or Madam\nWelcome to Bank of Revature\nHow are y'all today?\n");
//		System.out.println("Would you like to\n\t1: Register\n\t2: Login\n\t3: Exit");
		Menu m = new Menu();
		m.intro();
		
		try {
			Scanner s = new Scanner(System.in);
			//User test = new User("test", "ing");
			
			while(s.hasNext()) {
				String st = s.next();
				String uIn = st.toLowerCase();
				String uFName;
				String uLName;
				String uName;
				String uPass;
				
//				if(uIn.equals("getaccnumfor:user")) {
//					System.out.println(test.hasAccount("test"));
//				}
				if(uIn.equals(register) || uIn.equals("1")) {
					System.out.println("Please enter your first name:");
					uFName = s.next();					
					System.out.println("Ok " + uFName + ", please tell me your last name:");
					uLName = s.next();
					System.out.println("And what should your username be?");
					uName = s.next();
					//Check if username is available
					System.out.println("Finally, set a password please");
					uPass = s.next();
					//Just creating a user here but would put into sql at this point
					User thisDude = new User(uFName, uLName, uName, uPass);
					System.out.println("Alrighty boss you're all signed up\nLogin to continue");
				}
				if(uIn.equals(login) || uIn.equals("2")) {
					System.out.println("Username:");
					//make sure it exists in the db
					System.out.println("Password:");
					//make sure it matches the corresponding username
					//if() allgravy the maybe have a bool for loggedIn = true
					//can maybe do that in the db
					//will change menus accordingly
					
				}	
				if(uIn.equals("exit") || uIn.equals("3")) {
					break;
				}
				if(uIn.equals("help") || uIn.equals("4")) {
					m.help();	
				}
			}
			s.close();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		//do I want to define key words to match against user input incase they want to perform an action
	
		
		//user dao
		//implements calls that service will use
		//UserOracle implements userdao
		//userService 
	}
}
