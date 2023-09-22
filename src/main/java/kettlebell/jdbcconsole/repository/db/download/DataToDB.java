package kettlebell.jdbcconsole.repository.db.download;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import kettlebell.jdbcconsole.exception.HandlerException;
import kettlebell.jdbcconsole.repository.db.util.Util;

public class DataToDB extends Util {

	private List<String> list;

	public DataToDB(List<String> list) {
		this.list = list;
	}

	public void setDataToDb() {
		if (list.isEmpty()) {
			new HandlerException("Error download data");
		} else {
			list.stream().forEach(this::downloadDataToDb);
		}
	}

	private void downloadDataToDb(String sql) {

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

				preparedStatement.executeUpdate();

			} catch (SQLException e) {
				new HandlerException(e).printError();
			}

		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
	}
}
