package kettlebell.jdbcconsole.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kettlebell.jdbcconsole.controller.inter.CourseModel;
import kettlebell.jdbcconsole.model.Course;
import kettlebell.jdbcconsole.model.Student;
import kettlebell.jdbcconsole.repository.db.impl.CoursesRepository;

import java.util.Optional;

public class ControllerCourse implements CourseModel {

	private CoursesRepository courseRepository;

	public ControllerCourse(CoursesRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public int add(String courseName, String description) {
		Course course = new Course();
		course.setName(courseName);
		course.setCourseDescription(description);
		return courseRepository.add(course);
	}

	@Override
	public String getAll() {
		List<Course> list = new ArrayList<>();
		list.addAll(courseRepository.getAll());
		if (list.isEmpty()) {
			return "";
		} else
			return list.stream().map(s -> String.format(
					"|id=%-3d|name course: %-15s|description: %s.",
					s.getId(),s.getName(), s.getCourseDescription()))
					.collect(Collectors.joining("\n"));
	}

	@Override
	public String getById(int id) {

		Optional<Course> opt = courseRepository.getById(id);

		if (opt.isPresent()) {
			Course course = opt.get();
			return String.format("|id=%-3d|name course: %-15s|description: %s.",
					course.getId(), course.getName(),
					course.getCourseDescription());
		} else return "";
	}

	@Override
	public int update(String courseName, String description, int id) {
		Course course = new Course();
		course.setName(courseName);
		course.setCourseDescription(description);
		course.setId(id);
		return courseRepository.update(course);
	}

	@Override
	public int remove(int id) {
		return courseRepository.remove(id);
	}

	@Override
	public String getAllStudentsToCourseWithName(String name) {
		List<Student> list = courseRepository.getAllStudentsToCourseWithName(name);
		if(list.isEmpty()) {
			return "no students";
		}else {
			return list.stream().map(s -> String
					.format("|id=%-3d| first name: %-15s| last name: %-15s|",
							s.getId(), s.getName(), s.getLastName()))
			.collect(Collectors.joining("\n"));
		}
	}
}
