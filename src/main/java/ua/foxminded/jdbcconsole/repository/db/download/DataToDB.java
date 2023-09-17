package ua.foxminded.jdbcconsole.repository.db.download;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import ua.foxminded.jdbcconsole.repository.db.util.Util;

public class DataToDB extends Util{
	
	public DataToDB(List<String> list) {
		list.stream().forEach(this::downloadDataToDB);
	}
	
	private void downloadDataToDB(String sql) {

		Connection connection = null;
		Statement statement = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
