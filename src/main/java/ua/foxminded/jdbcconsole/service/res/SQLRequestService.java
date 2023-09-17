package ua.foxminded.jdbcconsole.service.res;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.foxminded.jdbcconsole.repository.res.DataFromResources;

public class SQLRequestService {

	private DataFromResources dataFromResources;

	private List<Integer> listForTable;
	private int count = -1;
	private Random ran = new Random();
	private List<String> listRequest;

	public SQLRequestService(DataFromResources dataFromResources) {
		this.dataFromResources = dataFromResources;
		listRequest = new ArrayList<>();
		listRequest.add(dataFromResources.getTables());
		listRequest.add(getCoursesSQL());		
		listRequest.add(getGroupsSQL());
		randomGroup();
		listRequest.add(getStudentsSQL());
		listRequest.add(getStudCourSQL());
	}
	
	public List<String> getListRequest() {
		return listRequest;
	}

	private String getStudentsSQL() {

		return Stream
				.generate(() -> "('" + dataFromResources.getFirstNames().get(ran.nextInt(20)) + "','"
						+ dataFromResources.getLastNames().get(ran.nextInt(20)) + "'," + listForTable.get(countPlus())
						+ ")")
				.limit(200)
				.collect(Collectors.joining(",", "INSERT INTO students(first_name, last_name, group_id) VALUES", ";"));

	}

	private String getCoursesSQL() {

		return dataFromResources.getCourses().stream()
				.map(s -> "('" + s.substring(0, s.indexOf("_")) + "','" + s.substring(s.indexOf("_") + 1) + "')")
				.collect(Collectors.joining(",", "INSERT INTO courses(course_name, course_description) VALUES ", ";"));
	}

	private String getGroupsSQL() {
		return Stream
				.generate(() -> "('" + (char) (ran.nextInt(26) + 97) + (char) (ran.nextInt(26) + 97) + "-"
						+ String.format("%02d", ran.nextInt(100)) + "')")
				.limit(10).collect(Collectors.joining(",", "INSERT INTO groups(group_name) VALUES ", ",('non');"));
	}

	private String getStudCourSQL() {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO students_courses(student_id,course_id) VALUES ");
		int countCourses = 0;
		int indexCourse = 0;
		for (int i = 1; i <= 200; i++) {// id student
			countCourses = ran.nextInt(3) + 1;
			List<Integer> listCourse = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
			for (int j = 1; j <= countCourses; j++) {// count course
				indexCourse = ran.nextInt(listCourse.size());
				builder.append("(" + i + "," + listCourse.get(indexCourse) + "),");// id course ;
				listCourse.remove(indexCourse);
			}
		}
		builder.deleteCharAt(builder.length() - 1);

		builder.append(";");

		return builder.toString();
	}

	private void randomGroup() {
		List<Integer> listGroup = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
		listForTable = new ArrayList<>();
		List<Integer> result = new ArrayList<>();
		boolean end = true;
		while (end) {
			int z = ran.nextInt(listGroup.size());// get random index for id group
			int idGroup = listGroup.get(z);
			listGroup.remove(z);

			int countStudentsInGroup = ran.nextInt(21) + 10;

			Stream.generate(() -> idGroup).limit(countStudentsInGroup).forEach(result::add);
			if (listGroup.isEmpty())
				end = false;

		}
		int difference = result.size() - 200;
		if (difference < 0) {
			for (int i = 0; i < Math.abs(difference); i++) {
				result.add(11);
			}
			listForTable.addAll(result);
		} else if (difference > 0) {
			listForTable = result.subList(0, 200);
		}
	}

	private int countPlus() {
		count++;
		return count;
	}
}
