package com.revature.JDBCBank;

public class Menu {
	
	
	//Intro
	public void intro() {
		System.out.println("Hello Sir or Madam\nWelcome to Bank of Revature\nHow are y'all today?\n");
		System.out.println("Would you like to\n\t1: Register\n\t2: Login\n\t3: Exit\n\t4: Help");
	}
	
	public void register() {
		System.out.println("Please enter your first name:");
		//uFName = s.next();					
		System.out.println("Ok, please tell me your last name:");
		//uLName = s.next();
		System.out.println("And what should your username be?");
		//uName = s.next();
		//Check if username is available
		System.out.println("Finally, set a password please");
		//uPass = s.next();
		//Just creating a user here but would put into sql at this point
		//User thisDude = new User(uFName, uLName, uName, uPass);
		System.out.println("Alrighty boss you're all signed up\nLogin to continue");
	}
	
	public void login() {
		System.out.println("Username:");
		//make sure it exists in the db
		System.out.println("Password:");
		//make sure it matches the corresponding username
		//if() allgravy the maybe have a bool for loggedIn = true
		//can maybe do that in the db
		//will change menus accordingly
	}

	
	public void help() {
		System.out.println("Type 'Register' or '1' to register a user\n"
				+ "Type 'Login' or '2' to login\n"
				+ "Type 'Exit' or '3' to exit\n"
				+ "Type 'Help' or '4' for a repeat\n");
	}
}
