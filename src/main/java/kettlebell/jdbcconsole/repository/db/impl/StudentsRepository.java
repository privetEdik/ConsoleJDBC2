package kettlebell.jdbcconsole.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import kettlebell.jdbcconsole.dao.db.StudentsDAO;
import kettlebell.jdbcconsole.exception.HandlerException;
import kettlebell.jdbcconsole.model.Student;
import kettlebell.jdbcconsole.repository.db.util.Util;

public class StudentsRepository extends Util implements StudentsDAO {
	
	private static Integer numberOfObjectsChanged;
	private static final String SQL_GET_ID_NON= "SELECT group_id FROM groups WHERE group_name='non';"; 
	private static final String SQL_ADD = "INSERT INTO  students (first_name, last_name, group_id) VALUES(?,?,?);"; 
	private static final String SQL_GET_ALL = "SELECT s.student_id, s.first_name, s.last_name, g.group_name "
			+ "FROM students AS s "
			+ "LEFT JOIN groups AS g ON s.group_id=g.group_id ORDER BY s.student_id;";
	private static final String SQL_GET_BY_ID = "SELECT s.student_id, s.first_name, s.last_name, g.group_name "
			+ "FROM students AS s "
			+ "LEFT JOIN groups AS g ON s.group_id=g.group_id "
			+ "WHERE s.student_id=?;";
	private static final String SQL_UPDATE = "UPDATE students SET first_name=?, last_name=? WHERE student_id=?;";
	
	private static final String SQL_DEL_STUD_COURS = "DELETE FROM students_courses WHERE student_id=?;";
	private static final String SQL_DELETE = "DELETE FROM students WHERE student_id=?;";
	private static final String SQL_ADD_ON_COURSE = "INSERT INTO students_courses VALUES(?,?);";
	private static final String SQL_REM_STUD_COURSE = "DELETE FROM students_courses WHERE student_id=? AND course_id=?;";
	private static final String SQL_ADD_STUD_GROUP = "UPDATE students SET group_id=? WHERE student_id=?;";
	private static final String SQL_DEL_STUD_GROUP = SQL_ADD_STUD_GROUP;
	private static final String SQL_GET_ALL_COURS_STUD = "SELECT ss.student_id, ss.first_name, ss.last_name, sc.course_name "
			+ "FROM school.students AS ss "
			+ "LEFT JOIN school.students_courses AS ss_c ON "
			+ "ss.student_id=ss_c.student_id "
			+ "LEFT JOIN school.courses AS sc ON " + "ss_c.course_id=sc.course_id "
			+ "WHERE ss.student_id =?;";
	
	
	@Override
	public int add(Student student) {

		numberOfObjectsChanged = 0;
		
		int idNon = 0;
		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ID_NON)) {

				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					idNon = resultSet.getInt("group_id");
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
				return numberOfObjectsChanged;
			}
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD)) {
				
				preparedStatement.setString(1, student.getName());
				preparedStatement.setString(2, student.getLastName());
				preparedStatement.setInt(3, idNon);
				
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
	public List<Student> getAll() {
		List<Student> studentsList = new ArrayList<>();

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL)) {

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					Student student = new Student();
					student.setId(resultSet.getInt("student_id"));
					student.setName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					student.setGroupName(resultSet.getString("group_name"));
					studentsList.add(student);
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return studentsList;
	}

	@Override
	public Optional<Student> getById(int id) {
		Student student = null;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {

				preparedStatement.setInt(1, id);

				ResultSet resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					student = new Student();
					student.setId(resultSet.getInt("student_id"));
					student.setName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					student.setGroupName(resultSet.getString("group_name"));
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return Optional.ofNullable(student);
	}

	@Override
	public int update(Student student) {
		numberOfObjectsChanged = 0;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE)) {

				preparedStatement.setString(1, student.getName());
				preparedStatement.setString(2, student.getLastName());
				preparedStatement.setInt(3, student.getId());

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
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DEL_STUD_COURS+SQL_DELETE)) {

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
	public int addStudentOnCourseFromList(Student student) {
		numberOfObjectsChanged = 0;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_ON_COURSE)) {

				preparedStatement.setInt(1, student.getId());
				preparedStatement.setInt(2, student.getCourseId());

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
	public int removeStudentOnCourseFromList(Student student) {
		numberOfObjectsChanged = 0;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REM_STUD_COURSE)) {

				preparedStatement.setInt(1, student.getId());
				preparedStatement.setInt(2, student.getCourseId());

				numberOfObjectsChanged = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return numberOfObjectsChanged;
	}

	// --------------------------------------------------------
	@Override
	public int addStudentToGroup(Student student) {
		int numberOfObjectsChanged = 0;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_STUD_GROUP)) {

				preparedStatement.setInt(1, student.getGroupId());
				preparedStatement.setInt(2, student.getId());

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
	public int removeStudentFromGroup(int idStudent) {
		numberOfObjectsChanged = 0;
		
		int idNon = 0;
		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ID_NON)) {

				ResultSet resultSet = preparedStatement.executeQuery();
				if(resultSet.next()) {
					idNon = resultSet.getInt("group_id");
				}
			} catch (SQLException e) {
				new HandlerException(e).printError();
				return numberOfObjectsChanged;
			}
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DEL_STUD_GROUP)) {
				
				preparedStatement.setInt(1, idNon);
				preparedStatement.setInt(2, idStudent);

				numberOfObjectsChanged = preparedStatement.executeUpdate();
			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return numberOfObjectsChanged;
	}

	// --------------------------------------------------------
	@Override
	public Optional<Student> getAllCoursesStudent(int idStudent) {
		List<String> courses = new ArrayList<>();
		Student student = null;

		try (Connection connection = getConnection()) {
			try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_COURS_STUD)) {

				preparedStatement.setInt(1, idStudent);
				ResultSet resultSet = preparedStatement.executeQuery();

				if (resultSet.next()) {
					student = new Student();
					student.setId(resultSet.getInt("student_id"));
					student.setName(resultSet.getString("first_name"));
					student.setLastName(resultSet.getString("last_name"));
					courses.add( Optional.ofNullable(resultSet.getString("course_name")).orElse("no courses"));

					while (resultSet.next()) {
						courses.add(resultSet.getString("course_name"));
					}
					student.setCourses(courses);
				}

			} catch (SQLException e) {
				new HandlerException(e).printError();
			}
		} catch (SQLException e) {
			new HandlerException(e).printError();
		}
		return Optional.ofNullable(student);
	}
}
