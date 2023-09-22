package kettlebell.jdbcconsole.main;

import kettlebell.jdbcconsole.controller.impl.ControllerCourse;
import kettlebell.jdbcconsole.controller.impl.ControllerGroups;
import kettlebell.jdbcconsole.controller.impl.ControllerStudent;
import kettlebell.jdbcconsole.repository.db.download.DataToDB;
import kettlebell.jdbcconsole.repository.db.impl.CoursesRepository;
import kettlebell.jdbcconsole.repository.db.impl.GroupsRepository;
import kettlebell.jdbcconsole.repository.db.impl.StudentsRepository;
import kettlebell.jdbcconsole.repository.res.DataFromResources;
import kettlebell.jdbcconsole.service.res.SQLRequestService;
import kettlebell.jdbcconsole.view.View;

public class Domain {

	public static void main(String[] args) {
	new DataToDB((new SQLRequestService(new DataFromResources())).getListRequest());
		 new View(
				new ControllerStudent(new StudentsRepository()),
				new ControllerCourse(new CoursesRepository()),
				new ControllerGroups(new GroupsRepository())
				).mainDialog();		 
	}
}

