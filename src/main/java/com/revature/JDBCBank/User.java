package com.revature.JDBCBank;

import java.io.Serializable;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2124431612922891183L;
	private String fName;
	private String lName;
	private String userName;
	transient private String pass;
	private int numOfAccs = 0;
	private int numOfUsers = 0;
	
}
