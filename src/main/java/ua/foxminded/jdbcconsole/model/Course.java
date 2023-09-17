package ua.foxminded.jdbcconsole.model;

//import java.util.List;

public class Course extends Model{
	
	//private String CourseName;
	private String courseDescription;
	//private List<Student> students;
	
	public Course() {
		
	}
	
	public Course(String str) {
		this.setName(str.substring(0,str.indexOf("_"))); 
		this.courseDescription = str.substring(str.indexOf("_")+1);
	}

//	public String getCouName() {
//		return CourseName;
//	}
//	public void setCourseName(String courseName) {
//		CourseName = courseName;
//	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	/*public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}*/

}
