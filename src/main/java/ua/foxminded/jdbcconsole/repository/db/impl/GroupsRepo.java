package ua.foxminded.jdbcconsole.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.jdbcconsole.dao.db.GroupsDAO;
import ua.foxminded.jdbcconsole.model.Group;
import ua.foxminded.jdbcconsole.repository.db.util.Util;

public class GroupsRepo extends Util implements GroupsDAO {

	//Connection connection = getConnection();

	@Override
	public int add(Group group) {
		PreparedStatement preparedStatement = null;
		int assignedIdGroup = 0;
		String sql = "insert into school.groups (group_name) values(?);";

		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql,new String[] {"group_id"});

			preparedStatement.setString(1, group.getName());

			preparedStatement.executeUpdate();
			
			ResultSet gk = preparedStatement.getGeneratedKeys();
			if(gk.next()) {
				assignedIdGroup = gk.getInt("group_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
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
		return assignedIdGroup;
	}

	@Override
	public List<Group> getAll() {
		List<Group> groupsList = new ArrayList<>();

		String sql = "SELECT group_id, group_name FROM school.groups ORDER BY group_id;";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				Group group = new Group();
				group.setId(resultSet.getInt("group_id"));
				group.setName(resultSet.getString("group_name"));

				groupsList.add(group);
			}
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
		return groupsList;
	}

	@Override
	public Group getById(int id) {
		PreparedStatement preparedStatement = null;

		String sql = "SELECT group_id, group_name FROM school.groups where group_id=?;";

		Group group = new Group();
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				group.setId(resultSet.getInt("group_id"));
				group.setName(resultSet.getString("group_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
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
		return group;
	}

	@Override
	public int update(Group group) {
		
		int result = 0;
		PreparedStatement preparedStatement = null;

		String sql = "update school.groups set group_name=? where group_id=?;";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, group.getName());
			preparedStatement.setInt(2, group.getId());

			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
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
		return result;
	}

	@Override
	public int remove(int id) {
		int result = 0;
		PreparedStatement preparedStatement = null;

		String sql = "DELETE FROM school.groups CASCADE where group_id=?;";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, id);

			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
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
		return result;
	}

	@Override
	public List<Group> getGroupsAndStudentsCount() {
		List<Group> listGroupsAndStudents = new ArrayList<>();
		Statement statement = null;
		
		String sql = "SELECT COUNT(ss.student_id),sg.group_name "
				+ "FROM school.groups AS sg "
				+ "LEFT JOIN school.students AS ss ON "
				+ "sg.group_id = ss.group_id "
				+ "GROUP BY sg.group_name";
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				Group group = new Group();
				group.setNumberOfStudents(resultSet.getInt("count"));
				group.setName(resultSet.getString("group_name"));
				listGroupsAndStudents.add(group);
			}
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
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return listGroupsAndStudents;
		
	}
}
