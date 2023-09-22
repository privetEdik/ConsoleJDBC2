package kettlebell.jdbcconsole.repository.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
	/* private static final String DB_DRIVER = "orc.h2.driver"; */

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=school";
	private static final String DB_USERNAME = "postgres";
	private static final String DB_PASSWORD = "1234";

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			//System.out.println("Java JDBC PostgreSQL Example");
			//System.out.println("Connected to PostgreSQL database!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Connection ERROR!");
		}
		return connection;
	}
}
