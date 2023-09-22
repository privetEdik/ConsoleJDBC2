package kettlebell.jdbcconsole.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import kettlebell.jdbcconsole.controller.inter.StudentModel;
import kettlebell.jdbcconsole.model.Student;
import kettlebell.jdbcconsole.repository.db.impl.StudentsRepository;

public class ControllerStudent implements StudentModel {


	private StudentsRepository studentsServise;

	public ControllerStudent(StudentsRepository studentsServise) {
		this.studentsServise = studentsServise;
	}

	@Override
	public int add(String firstName, String lastName) {
		Student student = new Student();
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
							 s.getId(),s.getName(),s.getLastName(),s.getGroupName()))
					.collect(Collectors.joining("\n"));
	}

	@Override
	public String getById(int id) {
		Optional<Student> opt = studentsServise.getById(id);
		
		if (opt.isPresent()) {
			Student stud = opt.get();
			return String.format("|id=%-3d| first name: %-15s| last name: %-15s| id group: %-2s",
					stud.getId(), stud.getName(), stud.getLastName(), stud.getGroupName());
		} else
			return "";

	}

	@Override
	public int update(String firstName, String lastName, int idStudent) {
		Student student = new Student();
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
		Student student = new Student();
		student.setId(idStudent);
		student.setGroupId(idGroup);
		return studentsServise.addStudentToGroup(student);
	}

	@Override
	public int addStudentOnCourseFromList(int idStudent, int idCourse) {
		Student student = new Student();
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
		Student student = new Student();
		student.setId(idStudent);
		student.setCourseId(idCourse);
		
		return studentsServise.removeStudentOnCourseFromList(student);
	}

	@Override
	public String getAllCourseStudent(int idStudent) {
		Optional<Student> opt = studentsServise.getAllCoursesStudent(idStudent);
		
		if (opt.isPresent()) {
			Student student = opt.get();
			return String.format("|id=%-3d| first name: %-15s| last name: %-15s| courses: %s",
					student.getId(), student.getName(), student.getLastName(),student.getCourses().stream()
					.collect(Collectors.joining(" ")));
		} else {
			return "";
		}
	}
}
