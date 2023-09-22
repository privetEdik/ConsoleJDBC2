package kettlebell.jdbcconsole.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kettlebell.jdbcconsole.dao.db.GroupsDAO;
import kettlebell.jdbcconsole.exception.HandlerException;
import kettlebell.jdbcconsole.model.Group;
import kettlebell.jdbcconsole.repository.db.util.Util;

public class GroupsRepository extends Util implements GroupsDAO {
	
	private static Integer numberOfObjectsChanged;
	private static final String SQL_ADD = "INSERT INTO groups (group_name) VALUES(?);";
	private static final String SQL_GET_ALL = "SELECT group_id, group_name FROM groups ORDER BY group_id;";
	private static final String SQL_BY_ID = "SELECT group_id, group_name FROM groups WHERE group_id=?;";
	private static final String SQL_UPDATE = "UPDATE groups SET group_name=? WHERE group_id=?;";
	private static final String SQL_UPDATE_IN_STUDENTS = "UPDATE students SET group_id=? WHERE group_id=?;";
	private static final String SQL_DELETE_FROM_GROUPS = "DELETE FROM groups WHERE group_id=?;";
	private static final String SQL_GROUPS_AND_STUDENDS_COUNT = "SELECT COUNT(s.student_id), g.group_name "
																				+ "FROM groups AS g "
																				+ "LEFT JOIN students AS s ON "
																				+ "g.group_id = s.group_id "
																				+ "GROUP BY g.group_name;";
	
	@Override
	public int add(Group group) {

		numberOfObjectsChanged = 0;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD)) {

				preparedStatement.setString(1, group.getName());
				numberOfObjectsChanged = preparedStatement.executeUpdate();

			} catch (SQLException e) {
				new HandlerException(e).printError();
			}

		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return numberOfObjectsChanged;
	}

	@Override
	public List<Group> getAll() {
		List<Group> groupsList = new ArrayList<>();

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					Group group = new Group();
					group.setId(resultSet.getInt("group_id"));
					group.setName(resultSet.getString("group_name"));

					groupsList.add(group);
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return groupsList;
	}

	@Override
	public Optional<Group> getById(int id) {
		Group group = null;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_BY_ID)) {

				preparedStatement.setInt(1, id);

				ResultSet resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					group = new Group();
					group.setId(resultSet.getInt("group_id"));
					group.setName(resultSet.getString("group_name"));
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return Optional.ofNullable(group);
	}

	@Override
	public int update(Group group) {
		numberOfObjectsChanged = 0;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

				preparedStatement.setString(1, group.getName());
				preparedStatement.setInt(2, group.getId());

				numberOfObjectsChanged = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return numberOfObjectsChanged;
	}

	@Override
	public int remove(int id) {		
		numberOfObjectsChanged = 0;		
		if(id == 11) {
			return numberOfObjectsChanged;
		}
		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_IN_STUDENTS+SQL_DELETE_FROM_GROUPS)) {

				preparedStatement.setInt(1, 11);
				preparedStatement.setInt(2, id);
				preparedStatement.setInt(3, id);

				numberOfObjectsChanged = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				new HandlerException(e).printError();
				return numberOfObjectsChanged;
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return numberOfObjectsChanged;
	}

	@Override
	public List<Group> getGroupsAndStudentsCount() {
		List<Group> listGroupsAndStudents = new ArrayList<>();

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GROUPS_AND_STUDENDS_COUNT)) {

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					Group group = new Group();
					group.setNumberOfStudents(resultSet.getInt("count"));
					group.setName(resultSet.getString("group_name"));
					listGroupsAndStudents.add(group);
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return listGroupsAndStudents;
	}
}
