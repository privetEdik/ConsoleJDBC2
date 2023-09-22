package kettlebell.jdbcconsole.dao.db;

import java.util.Optional;

import kettlebell.jdbcconsole.model.Student;

public interface StudentsDAO extends ItemDAO<Student> {

	int addStudentToGroup(Student student);
	
	int removeStudentFromGroup(int idStudent);
	
	Optional<Student> getAllCoursesStudent(int idStudent);
	
	int addStudentOnCourseFromList(Student student);
	
	int removeStudentOnCourseFromList(Student student);	

}
