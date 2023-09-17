package ua.foxminded.jdbcconsole.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ua.foxminded.jdbcconsole.controller.inter.StudentModel;
import ua.foxminded.jdbcconsole.model.Student;
import ua.foxminded.jdbcconsole.repository.db.impl.StudentsRepo;

public class ControllerStudent implements StudentModel {

	private Student student;
	private StudentsRepo studentsServise;

	public ControllerStudent(Student student, StudentsRepo studentsServise) {
		this.student = student;
		this.studentsServise = studentsServise;
	}

	@Override
	public int add(String firstName, String lastName) {
		student.setName(firstName);
		student.setLastName(lastName);
		return studentsServise.add(student);
	}

	@Override
	public String getAll() {
		List<Student> list = new ArrayList<>();
		list.addAll(studentsServise.getAll());
		if (list.isEmpty()) {
			return "";
		} else
			return list.stream()
					.map(s -> String.format("|id=%-3d| first name: %-15s| last name: %-15s| name group: %s",
							 s.getId(),s.getName(),s.getLastName(),outsideTheGroup(s.getGroupName())))
					.collect(Collectors.joining("\n"));
	}

	@Override
	public String getById(int id) {
		student = studentsServise.getById(id);
		if (student != null) {			
			return String.format("|id=%-3d| first name: %-15s| last name: %-15s| id group: %-2s",
					student.getId(), student.getName(), student.getLastName(), outsideTheGroup(student.getGroupName()));
		} else
			return "";

	}

	@Override
	public int update(String firstName, String lastName, int idStudent) {
		student.setName(firstName);
		student.setLastName(lastName);
		student.setId(idStudent);
		return studentsServise.update(student);
	}

	@Override
	public int remove(int id) {
		return studentsServise.remove(id);
	}

	@Override
	public int addStudentToGroup(int idStudent, int idGroup) {
		student.setId(idStudent);
		student.setGroupId(idGroup);
		return studentsServise.addStudentToGroup(student);
	}

	@Override
	public int addStudentOnCourseFromList(int idStudent, int idCourse) {
		student.setId(idStudent);
		student.setCourseId(idCourse);
		return studentsServise.addStudentOnCourseFromList(student);
	}

	@Override
	public int removeStudentFromGroup(int idStudent) {
		return studentsServise.removeStudentFromGroup(idStudent);
	}

	@Override
	public int removeStudentFromCourse(int idStudent, int idCourse) {
		student.setId(idStudent);
		student.setCourseId(idCourse);
		
		return studentsServise.removeStudentOnCourseFromList(student);
	}

	@Override
	public String getAllCourseStudent(int idStudent) {
		student = studentsServise.getAllCoursesStudent(idStudent);
		
		if (student != null) {
			List<String> list = student.getCourses();
			//list.addAll(student.getCourses());
			String str;
			if(list.get(0)==null) {
				str = "no courses";
			}else { 
				str = list.stream()
					.collect(Collectors.joining(" "));
			}
			return String.format("|id=%-3d| first name: %-15s| last name: %-15s| courses: %s",
					student.getId(), student.getName(), student.getLastName(),str);
		} else {
			return "";
		}
	}

	private String outsideTheGroup(String i) {
		String strGroup = i;
		if (i==null) {
			strGroup = "not included in the group";
		}
		return strGroup;
	}
}
