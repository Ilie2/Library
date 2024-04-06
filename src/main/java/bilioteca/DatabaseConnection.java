package bilioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	static String password = "pass";
	static String user = "user";


	
	public static  void dispaySQLExceptions(SQLException ex) {
		while (ex != null) {
			System.out.println("SQL State:" + ex.getSQLState());
			System.out.println("Error Code:" + ex.getErrorCode());
			System.out.println("Message:" + ex.getMessage());
			Throwable t = ex.getCause();
			while (t != null) {
				System.out.println("Cause:" + t);
				t = t.getCause();
			}
			ex = ex.getNextException();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:derby://localhost:1527/tres;create=true";
		return  DriverManager.getConnection(url);
	}

}

