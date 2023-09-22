package kettlebell.jdbcconsole.dao.db;



import java.util.List;

import kettlebell.jdbcconsole.model.Course;
import kettlebell.jdbcconsole.model.Student;;

public interface CoursesDAO extends ItemDAO<Course>  {
	List<Student> getAllStudentsToCourseWithName(String name);
}
