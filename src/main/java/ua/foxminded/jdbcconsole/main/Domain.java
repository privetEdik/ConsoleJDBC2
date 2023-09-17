package ua.foxminded.jdbcconsole.main;

import ua.foxminded.jdbcconsole.controller.impl.ControllerCourse;
import ua.foxminded.jdbcconsole.controller.impl.ControllerGroups;
import ua.foxminded.jdbcconsole.controller.impl.ControllerStudent;
import ua.foxminded.jdbcconsole.model.Course;
import ua.foxminded.jdbcconsole.model.Group;
import ua.foxminded.jdbcconsole.model.Student;
import ua.foxminded.jdbcconsole.repository.db.download.DataToDB;
import ua.foxminded.jdbcconsole.repository.db.impl.CoursesRepo;
import ua.foxminded.jdbcconsole.repository.db.impl.GroupsRepo;
import ua.foxminded.jdbcconsole.repository.db.impl.StudentsRepo;
import ua.foxminded.jdbcconsole.repository.res.DataFromResources;
import ua.foxminded.jdbcconsole.service.res.SQLRequestService;
import ua.foxminded.jdbcconsole.view.View;

public class Domain {

	public static void main(String[] args) {
	new DataToDB((new SQLRequestService(new DataFromResources())).getListRequest());
		 new View(
				new ControllerStudent(new Student(), new StudentsRepo()),
				new ControllerCourse(new Course(), new CoursesRepo()),
				new ControllerGroups(new Group(), new GroupsRepo())
				).mainDialog();
		 
	}

}

