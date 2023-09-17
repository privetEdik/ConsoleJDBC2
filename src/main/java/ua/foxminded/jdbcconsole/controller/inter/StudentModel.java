package ua.foxminded.jdbcconsole.controller.inter;

public interface StudentModel extends ItemModel {
	int add(String firstName, String lastName);

	// String getAll();

	// String getById(int id);

	int update(String firstName, String lastName, int idStudent);

	int addStudentToGroup(int idStudent, int idGroup);
	int addStudentOnCourseFromList(int idStudent, int idCourse);
	int removeStudentFromGroup(int idStudent);
	String getAllCourseStudent(int idStudent);
	int removeStudentFromCourse(int idStudent, int idCourse);

	// void remove(int id);
}
