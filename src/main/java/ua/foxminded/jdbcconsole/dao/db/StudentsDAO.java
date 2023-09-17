package ua.foxminded.jdbcconsole.dao.db;

import ua.foxminded.jdbcconsole.model.Student;

public interface StudentsDAO extends ItemDAO<Student> {

	int addStudentToGroup(Student student);
	
	int removeStudentFromGroup(int idStudent);
	
	Student getAllCoursesStudent(int idStudent);
	
	int addStudentOnCourseFromList(Student student);
	
	int removeStudentOnCourseFromList(Student student);	

}
