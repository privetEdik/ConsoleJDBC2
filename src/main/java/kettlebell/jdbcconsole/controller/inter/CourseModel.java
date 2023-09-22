package kettlebell.jdbcconsole.controller.inter;

public interface CourseModel extends ItemModel{
	int add(String courseName,String description);

	//String getAll();

	//String getById(int id);

	int update(String courseName,String description, int id);
	String getAllStudentsToCourseWithName(String name);
	//void remove(int id);
}
