package kh.com.acledabank.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public static ResultSet selectDB(String url, String username, String password, String query) {
		ResultSet resultSet = null;
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			statement.close();
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultSet;
	}
}
