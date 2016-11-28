//Demonstrates using OUT parameters with Stored Procedures

package com.rxp8385.jdbcExamples;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class GreetTheDepartment {

	public static void main(String[] args) throws SQLException {

		Connection myConn = null;
		CallableStatement myStmt = null;
		
		try {
			// Connect to the database
			myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/jdbcexample", "root", "pword");
			
			String theDepartment ="Engineering";
			
			// Prepare the stored procedure call
			myStmt = myConn.prepareCall("{call greet_the_department(?)}");
			
			// Set the parameters
			myStmt.registerOutParameter(1, Types.VARCHAR);
			myStmt.setString(1, theDepartment);
			
			// Call the stored procedure
			
			System.out.println("Calling stored procedure.  greet_the_department('"+ theDepartment +"')");
			myStmt.execute();
			System.out.println("Finished calling stored procedure");
			
			// Get the value of the INOUT parameter
			String theResult = myStmt.getString(1);
			
			System.out.println("\nThe result = " + theResult);
			
		} catch (Exception exc){
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt);
		}

	}

	private static void close(Connection myConn, Statement myStmt) throws SQLException {

		if(myStmt != null){
			myStmt.close();
		}
		
		if(myConn != null){
			myConn.close();
		}
		
	}

}
