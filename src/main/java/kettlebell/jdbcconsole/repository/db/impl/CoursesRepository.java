package kettlebell.jdbcconsole.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kettlebell.jdbcconsole.dao.db.CoursesDAO;
import kettlebell.jdbcconsole.exception.HandlerException;
import kettlebell.jdbcconsole.model.Course;
import kettlebell.jdbcconsole.model.Student;
import kettlebell.jdbcconsole.repository.db.util.Util;

public class CoursesRepository extends Util implements CoursesDAO {
	
		private static Integer numberOfObjectsChanged;
		private static final String SQL_ADD = "INSERT INTO courses (course_name, course_description) VALUES(?,?);";
		private static final String SQL_GET_ALL = "SELECT course_id, course_name, course_description FROM courses ORDER BY course_id;";
		private static final String SQL_GET_BY_ID = "SELECT course_id, course_name, course_description FROM courses WHERE course_id=?;";
		private static final String SQL_UPDATE = "UPDATE courses SET course_name=?, course_description=? WHERE course_id=?;";
		private static final String SQL_REMOVE_SUDENT_COURSE ="DELETE FROM students_courses WHERE course_id=?;";
		private static final String SQL_REMOVE = "DELETE FROM courses WHERE course_id=?;";
		private static final String SQL_GET_ALL_STUDENTS = "SELECT  s.student_id, s.first_name, s.last_name "
																		+ "FROM courses AS c "
																		+ "INNER JOIN students_courses AS s_c ON c.course_id=s_c.course_id "
																		+ "INNER JOIN students AS s ON s_c.student_id=s.student_id "
																		+ "WHERE c.course_name=?;";
		
		@Override
	public int add(Course course) {

		numberOfObjectsChanged = 0;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD)) {

				preparedStatement.setString(1, course.getName());
				preparedStatement.setString(2, course.getCourseDescription());

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
	public List<Course> getAll() {
		List<Course> coursesList = new ArrayList<>();

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					Course course = new Course();
					course.setId(resultSet.getInt("course_id"));
					course.setName(resultSet.getString("course_name"));
					course.setCourseDescription(resultSet.getString("course_description"));

					coursesList.add(course);
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return coursesList;
	}

	@Override
	public Optional<Course> getById(int id) {

		Course course = null;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {

				preparedStatement.setInt(1, id);

				ResultSet resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					course = new Course();
					course.setId(resultSet.getInt("course_id"));
					course.setName(resultSet.getString("course_name"));
					course.setCourseDescription(resultSet.getString("course_description"));
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return Optional.ofNullable(course);
	}

	@Override
	public int update(Course course) {

		numberOfObjectsChanged = 0;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

				preparedStatement.setString(1, course.getName());
				preparedStatement.setString(2, course.getCourseDescription());
				preparedStatement.setInt(3, course.getId());

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

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_SUDENT_COURSE+SQL_REMOVE)) {
				
				preparedStatement.setInt(1, id);
				preparedStatement.setInt(2, id);
				
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
	public List<Student> getAllStudentsToCourseWithName(String name) {
		List<Student> list = new ArrayList<>();

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_STUDENTS)) {

				preparedStatement.setString(1, name);

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					Student student = new Student();
					student.setId(resultSet.getInt("student_id"));
					student.setName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					list.add(student);
				}

			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return list;
	}

}
