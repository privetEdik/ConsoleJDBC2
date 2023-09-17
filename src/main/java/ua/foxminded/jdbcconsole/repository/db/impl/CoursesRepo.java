package ua.foxminded.jdbcconsole.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.jdbcconsole.dao.db.CoursesDAO;
import ua.foxminded.jdbcconsole.model.Course;
import ua.foxminded.jdbcconsole.model.Student;
import ua.foxminded.jdbcconsole.repository.db.util.Util;

public class CoursesRepo extends Util implements CoursesDAO {

	// Connection connection = getConnection();

	@Override
	public int add(Course course) {
		PreparedStatement preparedStatement = null;
		int assignedIdCourse = 0;
		String sql = "insert into school.courses (course_name, course_description) values(?,?)";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getCourseDescription());

			assignedIdCourse = preparedStatement.executeUpdate();
			//ResultSet gk = preparedStatement.getGeneratedKeys();
			
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
		return assignedIdCourse;
	}

	@Override
	public List<Course> getAll() {
		List<Course> coursesList = new ArrayList<>();

		String sql = "SELECT course_id, course_name, course_description FROM school.courses ORDER BY course_id";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				Course course = new Course();
				course.setId(resultSet.getInt("course_id"));
				course.setName(resultSet.getString("course_name"));
				course.setCourseDescription(resultSet.getString("course_description"));

				coursesList.add(course);
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
		return coursesList;
	}

	@Override
	public Course getById(int id) {
		PreparedStatement preparedStatement = null;

		String sql = "SELECT course_id, course_name, course_description FROM school.courses where course_id=?";

		Course course = new Course();
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				course.setId(resultSet.getInt("course_id"));
				course.setName(resultSet.getString("course_name"));
				course.setCourseDescription(resultSet.getString("course_description"));
			}
			// preparedStatement.executeUpdate();
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
		return course;
	}

	@Override
	public int update(Course course) {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String sql = "update school.courses set course_name=?, course_description=? where course_id=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getCourseDescription());
			preparedStatement.setInt(3, course.getId());

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

		String sql = "DELETE FROM school.courses CASCADE where course_id=?";
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
	public List<Student> getAllStudentsToCourseWithName(String name) {
		List<Student> list = new ArrayList<>();
		PreparedStatement preparedStatement = null;

		String sql = "SELECT  ss.student_id, ss.first_name, ss.last_name " + "FROM school.courses AS sc "
				+ "INNER JOIN school.students_courses AS ss_c ON " + "sc.course_id=ss_c.course_id "
				+ "INNER JOIN school.students AS ss ON " + "ss_c.student_id=ss.student_id " + "WHERE sc.course_name=?";

		Student student;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, name);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				student = new Student();
				student.setId(resultSet.getInt("student_id"));
				student.setName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				list.add(student);
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
		return list;
	}

}
