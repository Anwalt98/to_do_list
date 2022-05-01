package application;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.event.ActionEvent;




public class SqlConnector {
	
private static final String url = "jdbc:mysql://localhost:3306/logins";

private static final String sqluser = "root";
private static final String sqlpass = "root";

private static Connection con;
private static Statement stmt;
private static ResultSet rs;

public static void deleteGoalSQL(int i) {
	int j = i + 1;

	String addStatement = "update users set goal" + i + " = goal" + j+" where username = \"" + Controller.finalUsername + "\";";
	String addStatement6 = "update users set goal6 = null where username = \"" + Controller.finalUsername + "\";";

    try {
     
    	
        con = DriverManager.getConnection(url, sqluser, sqlpass);

        // getting Statement object to execute query
        stmt = con.createStatement();

        // executing SELECT query
        while (j <= 6) {
       stmt.execute(addStatement);
       i++;
       ++j;
       addStatement = "update users set goal" + i + " = goal" + j+" where username = \"" + Controller.finalUsername + "\";";
        }
        if (j == 7) {
        	stmt.execute(addStatement6);
        }
    }
    catch (SQLIntegrityConstraintViolationException ex) {
    	
    }
    catch (SQLException sqlEx) {
        sqlEx.printStackTrace();
    } 
    
    finally {
        
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
    }
}


 

public static void register(String usernamesql, String passwordsql) {
	
  String addStatement = "insert into logins.users (username,password) values (\"" + usernamesql + "\",\"" + passwordsql +"\")";
	

    try {
     
    	
        con = DriverManager.getConnection(url, sqluser, sqlpass);

        // getting Statement object to execute query
        stmt = con.createStatement();

        // executing SELECT query
       stmt.execute(addStatement);
        }

    catch (SQLIntegrityConstraintViolationException ex) {
    	Controller c = new Controller();
c.usedNameAlert(new ActionEvent());
    }
    catch (SQLException sqlEx) {
        sqlEx.printStackTrace();
    } 
    
    finally {
        
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }

    }

}
public static boolean checkCoincidence(String user, String pass) throws SQLException{
	
	String query = "select password from users where username = \"" + user + "\";";
	String password = "";
	con = DriverManager.getConnection(url, sqluser, sqlpass);

    // getting Statement object to execute query
    stmt = con.createStatement();

  
    rs = stmt.executeQuery(query);
    while (rs.next()) {
    password = rs.getString(1);
    }
    if (password.equals(pass) && !(password.equals(""))) {
    	 try { con.close(); } catch(SQLException se) { /*can't do anything */ }
    	    try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
    	return true;
    }
    else {
    	 try { con.close(); } catch(SQLException se) { /*can't do anything */ }
    	    try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
    	return false;
    }
}

public static ArrayList<String> getGoals(String user){
	return new ArrayList<String>();
}

public static void setGoals(String goal,String numberOfGoalInSQL, String username) {
String addStatement = "update users set " + numberOfGoalInSQL + " = \"" + goal + "\" where username = \"" +username + "\";";
	

    try {
     
    	
        con = DriverManager.getConnection(url, sqluser, sqlpass);

        // getting Statement object to execute query
        stmt = con.createStatement();

        // executing SELECT query
       stmt.execute(addStatement);
        }

    catch (SQLIntegrityConstraintViolationException ex) {
    	Controller c = new Controller();
c.usedNameAlert(new ActionEvent());
    }
    catch (SQLException sqlEx) {
        sqlEx.printStackTrace();
    } 
    
    finally {
        
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }

    }
}
public static void findGoals(String user)  {
	try {
	String query1 = "select goal1 from users where username = \"" + user + "\";";
	String query2 = "select goal2 from users where username = \"" + user + "\";";
	String query3 = "select goal3 from users where username = \"" + user + "\";";
	String query4 = "select goal4 from users where username = \"" + user + "\";";
	String query5 = "select goal5 from users where username = \"" + user + "\";";
	String query6 = "select goal6 from users where username = \"" + user + "\";";
	
	String goal = "";
	con = DriverManager.getConnection(url, sqluser, sqlpass);

    // getting Statement object to execute query
    stmt = con.createStatement();

  
    rs = stmt.executeQuery(query1);
    while (rs.next()) {
    goal = rs.getString(1);
   User.getInstance().goals.add(goal);
    }
    rs = stmt.executeQuery(query2);
    while (rs.next()) {
    goal = rs.getString(1);
   User.getInstance().goals.add(goal);
    }
    rs = stmt.executeQuery(query3);
    while (rs.next()) {
    goal = rs.getString(1);
   User.getInstance().goals.add(goal);
    }
    rs = stmt.executeQuery(query4);
    while (rs.next()) {
    goal = rs.getString(1);
   User.getInstance().goals.add(goal);
    }
	rs = stmt.executeQuery(query5);
    while (rs.next()) {
    goal = rs.getString(1);
   User.getInstance().goals.add(goal);
    }
    rs = stmt.executeQuery(query6);
    while (rs.next()) {
    goal = rs.getString(1);
   User.getInstance().goals.add(goal);
   
    }}
	catch (SQLException e) {
	
}
	 
    finally {
        
        try { con.close(); } catch(SQLException se) { /*can't do anything */ }
        try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }

    }
}


}

