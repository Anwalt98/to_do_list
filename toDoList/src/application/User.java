package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	private static final String url = "jdbc:mysql://localhost:3306/logins";

	private static final String sqluser = "root";
	private static final String sqlpass = "root";
	static private String username;
	private String password;
	private static User instance;
	ArrayList <String> goals = new ArrayList<>();
	
	
 public void setGoals() throws SQLException {
	 String query = "select goal1 from users where username = \"" + username + "\";";
		String password = "";
	
			con = DriverManager.getConnection(url, sqluser, sqlpass);
		

	    // getting Statement object to execute query
	    stmt = con.createStatement();

	  
	    rs = stmt.executeQuery(query);
	    while (rs.next()) {
	    password = rs.getString(1);
	    }
 }
 public static User getInstance() {
	 if (instance == null) {
	 instance =  new User();
 } return instance;
}
 public static void setUsername(String name) {
	 getInstance().username = name;
 }
 public static String getUsername() {
	return getInstance().username;
 }
}
