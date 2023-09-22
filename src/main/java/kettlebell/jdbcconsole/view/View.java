package kettlebell.jdbcconsole.view;

import java.util.Scanner;
import java.util.regex.Pattern;

import kettlebell.jdbcconsole.controller.impl.ControllerCourse;
import kettlebell.jdbcconsole.controller.impl.ControllerGroups;
import kettlebell.jdbcconsole.controller.impl.ControllerStudent;

public class View {

	private static String table;
	private static String command;
	private static String oneOrAll;
	private static int id;
	private static int idStudent;
	private static int idCourse;
	private static int idGroup;

	private static int numberOfChangedObjects;
	private static String groupName;
	private static String courseName;
	private static String getResult;

	private static String firstName;
	private static String lastName;

	private static Scanner scanner = new Scanner(System.in);

	private static String regexNameStudent = "[A-z]{1,15}";
	private static String regexNameGroup = "[a-z]{2}\\-{1}[0-9]{2}";
	private static String regexNameCourse = "[a-z]{3,16}";
	private static String regexId = "\\d{1,3}";

	private ControllerStudent controllerStudent;
	private ControllerCourse controllerCourse;
	private ControllerGroups controllerGroup;

	public View(ControllerStudent controllerStudent, ControllerCourse controllerCourse,
			ControllerGroups controllerGroup) {
		this.controllerStudent = controllerStudent;
		this.controllerCourse = controllerCourse;
		this.controllerGroup = controllerGroup;
	}

	public void mainDialog() {
		System.out.println("Hi! Welcome to school application");
		do {
			numberOfChangedObjects=0;
			readCommandAndTable();

			switch (command) {
			case "1":// create
				if (table.equals("1")) {// student

					firstName = inputName(regexNameStudent, "first");
					lastName = inputName(regexNameStudent, "last");
					numberOfChangedObjects = controllerStudent.add(firstName, lastName);

				} else if (table.equals("2")) {// course
					courseName = inputName(regexNameCourse, "course");
					numberOfChangedObjects = controllerCourse.add(courseName, inputDescription());

				} else if (table.equals("3")) {// group
					groupName = inputName(regexNameGroup, "group");
					numberOfChangedObjects = controllerGroup.add(groupName);
				} else {
					error();
				}

				break;
			case "2":// get-----------------------------
				if (table.equals("1")) {// student
					getAllOrOneObject("students");
					if (id == 0) {
						getResult = controllerStudent.getAll();
						if (getResult.length() == 0) {
							numberOfChangedObjects = 0;
						} else {
							System.out.println(getResult);
							numberOfChangedObjects = -1;
						}
					} else {
						getResult = controllerStudent.getById(id);
						if (getResult.length() == 0) {
							numberOfChangedObjects = 0;
						} else {
							System.out.println(getResult);
							numberOfChangedObjects = -1;
						}
					}

				} else if (table.equals("2")) {// course
					getAllOrOneObject("course");
					if (id == 0) {
						getResult = controllerCourse.getAll();
						if (getResult.length() == 0) {
							numberOfChangedObjects = 0;
						} else {
							System.out.println(getResult);
							numberOfChangedObjects = -1;
						}
					} else {
						getResult = controllerCourse.getById(id);
						if (getResult.length() == 0) {
							numberOfChangedObjects = 0;
						} else {
							System.out.println(getResult);
							numberOfChangedObjects = -1;
						}
					}

				} else if (table.equals("3")) {// group
					getAllOrOneObject("group");
					if (id == 0) {
						getResult = controllerGroup.getAll();
						if (getResult.length() == 0) {
							numberOfChangedObjects = 0;
						} else {
							System.out.println(getResult);
							numberOfChangedObjects = -1;
						}
					} else {
						getResult = controllerGroup.getById(id);
						if (getResult.length() == 0) {
							numberOfChangedObjects = 0;
						} else {
							System.out.println(getResult);
							numberOfChangedObjects = -1;
						}
					}

				} else
					error();

				break;
			case "3":// update-------------------------------------
				if (table.equals("1")) {// student
					inputIdNotNullObject("studenta where you want to update the data");
					firstName = inputName(regexNameStudent, "first");
					lastName = inputName(regexNameStudent, "last");
					numberOfChangedObjects = controllerStudent.update(firstName, lastName, id);

				} else if (table.equals("2")) {// course
					System.out.println(controllerCourse.getAll());
					inputIdNotNullObject("course where you want to update name");
					courseName = inputName(regexNameCourse, "course");
					numberOfChangedObjects = controllerCourse.update(courseName, inputDescription(), id);

				} else if (table.equals("3")) {// group
					System.out.println(controllerGroup.getAll());
					inputIdNotNullObject("group where you want to update name");
					groupName = inputName(regexNameGroup, "group");
					numberOfChangedObjects = controllerGroup.update(groupName, id);

				} else {
					error();
				}

				break;
			case "4":// delete-----------------------------------

				if (table.equals("1")) {// student
					inputIdNotNullObject("object to delete");
					numberOfChangedObjects = controllerStudent.remove(id);

				} else if (table.equals("2")) {// course
					inputIdNotNullObject("object to delete");
					numberOfChangedObjects = controllerCourse.remove(id);

				} else if (table.equals("3")) {// group
					inputIdNotNullObject("object to delete");
					numberOfChangedObjects = controllerGroup.remove(id);
				} else {
					error();
				}

				break;
			case "5":// add student in group(from a list)
				System.out.println(controllerStudent.getAll());
				inputIdNotNullObject("student");
				idStudent = id;
				System.out.println(controllerGroup.getAll());
				inputIdNotNullObject("group");
				idGroup = id;
				numberOfChangedObjects = controllerStudent.addStudentToGroup(idStudent, idGroup);

				break;
			case "6":// add student on course(from a list) (e)
				System.out.println(controllerStudent.getAll());
				inputIdNotNullObject("student");
				idStudent = id;
				System.out.println(controllerCourse.getAll());
				inputIdNotNullObject("course");
				idCourse = id;
				numberOfChangedObjects = controllerStudent.addStudentOnCourseFromList(idStudent, idCourse);
				break;
			case "7":// delete student with group
				System.out.println(controllerStudent.getAll());
				inputIdNotNullObject("student");
				numberOfChangedObjects = controllerStudent.removeStudentFromGroup(id);
				break;
			case "8":// delete student with course (f)
				System.out.println(controllerStudent.getAll());
				inputIdNotNullObject("student");
				idStudent = id;
				String str = controllerStudent.getAllCourseStudent(idStudent);
				if(str.equals("")) {
					numberOfChangedObjects = 0;
				}else {
					System.out.println(str);			
					System.out.println(controllerCourse.getAll());
					inputIdNotNullObject("course");
					idCourse = id;
					numberOfChangedObjects = controllerStudent.removeStudentFromCourse(idStudent, idCourse);
				}
				break;
			case "9":// Find all students related to course with given name (b)
				String result = controllerCourse.getAll();
				do {
					System.out.println(result);
					courseName = inputName(regexNameCourse, "course");
				} while ((result.indexOf(courseName) == -1));
				System.out.println(controllerCourse.getAllStudentsToCourseWithName(courseName));
				numberOfChangedObjects = 1;
				break;
			case "10":// Find all groups with less or equals student count (a)
				System.out.println(controllerGroup.getGroupsAndStudentsCount());
				numberOfChangedObjects = 1;
				break;
			case "11":// Find all courses of student
				inputIdNotNullObject("student");
				idStudent = id;
				getResult = controllerStudent.getAllCourseStudent(idStudent);
				if(getResult.equals("")) {
					numberOfChangedObjects=0;
				}else {
					System.out.println(getResult);
					numberOfChangedObjects = 1;
				}
				break;
			case "0":
				System.out.println("Good Bay!");
				numberOfChangedObjects = 1;
				break;

			default:
				error();
				break;

			}
			successfully(numberOfChangedObjects);

		} while (!command.equals("0"));

	}
	// -------------------------------------------Name--------------------------

	private void readCommandAndTable() {
		
		boolean b = false;
		System.out.println("\nwhat operation do you want to do with the data?\n"
				+ "create new (c)...........................................input 1\n"
				+ "get......................................................input 2\n"
				+ "update...................................................input 3\n"
				+ "delete (d)...............................................input 4\n"
				+ "add student in group(from a list)........................input 5\n"// update
				+ "add student on course(from a list) (e)...................input 6\n"
				+ "delete student with group................................input 7\n"// update
				+ "delete student with course (f)...........................input 8\n"
				+ "Find all students related to course with given name (b)..input 9\n"
				+ "Find all groups with less or equals student count (a)....input 10\n"
				+ "Find all courses of student..............................input 11\n"
				+ "exit.....................................................input 0\n"
				+ "----------------------------------------------------------------");

		command = scanner.nextLine();
		
		if (command.equals("1")||command.equals("2")||command.equals("3")||command.equals("4")) {
			b = true;
		}
		
		if (b) {

			boolean w = true;
			do {
				System.out.println("What object do you want to work with?\n"
						+ "student (c)(d)...input 1\n"
						+ "course...........input 2\n"
						+ "group............input 3\n"
						+ "------------------------------");
				table = scanner.nextLine();
				if (table.equals("1") || table.equals("2") || table.equals("3")) {
					w = false;
				} else
					error();
			} while (w);
		}
	}

	private String inputName(String regex, String stringFromOut) {
		boolean b = true;
		String name;
		do {
			System.out.println("Input " + stringFromOut + " name:");
			name = scanner.nextLine();

			if (Pattern.matches(regex, name)) {
				b = false;
			} else
				error();

		} while (b);
		return name;
	}

	private String inputDescription() {
		boolean b = true;
		String description;
		do {
			System.out.println("enter a description with no more than 256 characters");
			description = scanner.nextLine();

			if (description.length() <= 256) {
				b = false;
			} else
				error();

		} while (b);
		return description;

	}
	// ---------------------------------------------------------------------------------

	// ----------------------------------get---------------------------------------
	private void getAllOrOneObject(String object) {
		boolean b = true;

		do {
			System.out.println("get one or all " + object + "?\n" + "one  - input id\n" + "all  - input a\n"
					+ "------------------------------");
			oneOrAll = scanner.nextLine();

			if (oneOrAll.equals("a")) {
				b = false;
				id = 0;
			} else if (inputId(oneOrAll)) {
				b = false;
			} else {
				error();
			}

		} while (b);

	}

	// -----------------------------------------------------------------

	// ----------------------------------id---------------------------------------

	private void inputIdNotNullObject(String object) {
		boolean b = true;
		do {
			System.out.println("input id " + object);
			String number = scanner.nextLine();
			if (number == null) {
				error();
			} else if (inputId(number) ) {
				b = false;
			} else
				error();
		} while (b);

	}

	private boolean inputId(String arg) {
		if (Pattern.matches(regexId, arg)) {
			id = Integer.parseInt(arg);
			return true;
		} else
			return false;
	}

	// -----------------------------------------------------------------------------

	private void error() {
		System.out.println("Command not recognized! Please try again");
	}

	private void successfully(int i) {
		System.out.println("-------------------------------");
		if (i == 0) {
			System.out.println("operation failed! Data not found");
		} else if (i == -1) {
			System.out.println("operation GET was successful!");
		} else {System.out.println("operation was successful");}
		System.out.println("-------------------------------");
	}
}
