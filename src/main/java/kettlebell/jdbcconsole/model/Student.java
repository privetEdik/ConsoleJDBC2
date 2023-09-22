package kettlebell.jdbcconsole.model;

import java.util.List;

public class Student extends Model{

	private int groupId;
	private int courseId;
	private String lastName;
	private String groupName;
	
	private List<String> courses;
	
	public Student() {
		
	}
	
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getLastName() {
		return lastName;
	}
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}
	
}
