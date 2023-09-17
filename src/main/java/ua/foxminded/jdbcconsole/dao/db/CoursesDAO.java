package ua.foxminded.jdbcconsole.dao.db;



import java.util.List;

import ua.foxminded.jdbcconsole.model.Course;
import ua.foxminded.jdbcconsole.model.Student;;

public interface CoursesDAO extends ItemDAO<Course>  {
	List<Student> getAllStudentsToCourseWithName(String name);
}
