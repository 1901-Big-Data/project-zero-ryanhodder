package com.revature.JDBCBank;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import com.revature.Models.User;
import com.revature.Services.UserService;
import com.revature.Util.ConnectionUtil;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
	public static UserService us = UserService.getService();
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    
    public void testAddUser() {
    	ConnectionUtil.getConnection();
    	
    	try {
			ConnectionUtil.getConnection().setAutoCommit(false);
			
			assertSame(us.addUser("test", "test", "test", "test").get().getClass(), User.class);
			
		} catch (SQLException e) {
			fail(e.toString());
		} catch (NoSuchElementException e) {
			fail(e.toString());
		} finally {
			try {
				ConnectionUtil.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    }
    
	public void testUserOracleLoginWrongPassword() {
		ConnectionUtil.getConnection();	
		try {
			ConnectionUtil.getConnection().setAutoCommit(false);
				
			UserService.getService().login("test", "hello").get();
		} catch (SQLException e) {
			fail(e.toString());
		} catch (NoSuchElementException e) {
			//Expecting NoSuchElementException
		}
		finally {
			try {
				ConnectionUtil.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
