package ua.foxminded.jdbcconsole.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ua.foxminded.jdbcconsole.controller.inter.CourseModel;
import ua.foxminded.jdbcconsole.model.Course;
import ua.foxminded.jdbcconsole.repository.db.impl.CoursesRepo;

public class ControllerCourse implements CourseModel {
	
	private Course course;
	private CoursesRepo courseService;
	
	public ControllerCourse(Course course, CoursesRepo courseService) {
		this.course = course;
		this.courseService = courseService;
	}

	@Override
	public int add(String courseName,String description) {
		course.setName(courseName);
		course.setCourseDescription(description);
		return courseService.add(course);
	}

	@Override
	public String getAll() {
		List<Course> list = new ArrayList<>();
		list.addAll(courseService.getAll());
		if (list.isEmpty()) {
			return "";
		} else
			return list.stream()
				.map(s->String.format("|id=%-3d|name course: %-15s|description: %s.", s.getId(), s.getName(),s.getCourseDescription()))
				.collect(Collectors.joining("\n"));
	}

	@Override
	public String getById(int id) {
		course = courseService.getById(id);
		if(course!=null) {
			return String.format("|id=%-3d|name course: %-15s|description: %s.", course.getId(), course.getName(),course.getCourseDescription());	
		}else return "";
	}
	

	@Override
	public int update(String courseName,String description, int id) {
		course.setName(courseName);
		course.setCourseDescription(description);
		course.setId(id);
		return courseService.update(course);
	}

	@Override
	public int remove(int id) {
		return courseService.remove(id);
	}

	
	@Override
	public String getAllStudentsToCourseWithName(String name) {
		String str = courseService.getAllStudentsToCourseWithName(name).stream()
		.map(s-> String.format("|id=%-3d| first name: %-15s| last name: %-15s|",
				s.getId(),s.getName(), s.getLastName()))
		.collect(Collectors.joining("\n"));
		if(str.equals("")) {
			return "no students";
		}else return str;
	}

}
