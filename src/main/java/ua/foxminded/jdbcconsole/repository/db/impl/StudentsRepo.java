package ua.foxminded.jdbcconsole.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.foxminded.jdbcconsole.dao.db.StudentsDAO;
import ua.foxminded.jdbcconsole.model.Student;
import ua.foxminded.jdbcconsole.repository.db.util.Util;

public class StudentsRepo extends Util implements StudentsDAO {

	// Connection connection = getConnection();

	@Override
	public int add(Student student) {
		PreparedStatement preparedStatement = null;
		int assignedIdStudent = 0;
		String sql = "insert into school.students (first_name, last_name) values(?,?)";

		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, student.getName());
			preparedStatement.setString(2, student.getLastName());

			assignedIdStudent = preparedStatement.executeUpdate();
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
		return assignedIdStudent;
	}

	@Override
	public List<Student> getAll() {
		List<Student> studentsList = new ArrayList<>();
	
		String sql = "SELECT ss.student_id, ss.first_name, ss.last_name, sg.group_name FROM school.students AS ss "
				+ "LEFT JOIN school.groups AS sg ON ss.group_id=sg.group_id "
				+ "ORDER BY ss.student_id;";
		Statement statement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				Student student = new Student();
				student.setId(resultSet.getInt("student_id"));
				student.setName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				student.setGroupName(resultSet.getString("group_name"));
				studentsList.add(student);
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
		return studentsList;
	}

	@Override
	public Student getById(int id) {
		PreparedStatement preparedStatement = null;

		String sql = "SELECT student_id, first_name, last_name FROM school.students where student_id=?";

		Student student = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				student = new Student();
			student.setId(resultSet.getInt("student_id"));
			student.setName(resultSet.getString("first_name"));
			student.setLastName(resultSet.getString("last_name"));
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
		return student;
	}

	@Override
	public int update(Student student) {
		PreparedStatement preparedStatement = null;

		int result = 0;

		String sql = "update school.students set first_name=?, last_name=? where student_id=?";
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, student.getName());
			preparedStatement.setString(2, student.getLastName());
			preparedStatement.setInt(3, student.getId());

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
		PreparedStatement preparedStatement = null;
		int result = 0;

		String sql = "DELETE FROM school.students CASCADE WHERE student_id=?";
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
	public int addStudentOnCourseFromList(Student student) {

		PreparedStatement preparedStatement = null;

		int result = 0;
		String sql = "INSERT INTO school.students_courses VALUES(?,?)";

		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, student.getId());
			preparedStatement.setInt(2, student.getCourseId());

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
	public int removeStudentOnCourseFromList(Student student) {
		PreparedStatement preparedStatement = null;
		int result = 0;
		String sql = "DELETE FROM school.students_courses WHERE student_id=? AND course_id=?";

		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, student.getId());
			preparedStatement.setInt(2, student.getCourseId());

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

	// --------------------------------------------------------
	@Override
	public int addStudentToGroup(Student student) {
		int result = 0;
		PreparedStatement preparedStatement = null;
		String sql = "UPDATE school.students SET group_id=? WHERE student_id=?";

		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, student.getGroupId());
			preparedStatement.setInt(2, student.getId());

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
	public int removeStudentFromGroup(int idStudent) {
		PreparedStatement preparedStatement = null;
		int result = 0;
		String sql = "UPDATE school.students SET group_id=null WHERE student_id=?";

		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, idStudent);

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

	// --------------------------------------------------------
	@Override
	public Student getAllCoursesStudent(int idStudent) {
		List<String> courses = new ArrayList<>();
		Student student = null;
		String sql = "SELECT ss.student_id, ss.first_name, ss.last_name, sc.course_name " + "FROM school.students AS ss "
				+ "LEFT JOIN school.students_courses AS ss_c ON " + "ss.student_id=ss_c.student_id "
				+ "LEFT JOIN school.courses AS sc ON " + "ss_c.course_id=sc.course_id "
						+ "WHERE ss.student_id =?";
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, idStudent);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				student = new Student();
				student.setId(resultSet.getInt("student_id"));
				student.setName(resultSet.getString("first_name"));
				student.setLastName(resultSet.getString("last_name"));
				courses.add(resultSet.getString("course_name"));

				while (resultSet.next()) {
					courses.add(resultSet.getString("course_name"));
				}
				student.setCourses(courses);
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
		return student;
	}

}
