package kettlebell.jdbcconsole.service.res;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kettlebell.jdbcconsole.repository.res.DataFromResources;

public class SQLRequestService {

	private DataFromResources dataFromResources;
	
	private String createTables;
	private List<String> listFirstName;
	private List<String> listLastName;
	private List<String> listCourse;
	
	private List<Integer> listForTable;
	private Integer count = -1;
	private Random ran = new Random();
	private List<String> listRequest;
	private static final Integer startCountGroup = 10;

	public SQLRequestService(DataFromResources dataFromResources) {
		this.dataFromResources = dataFromResources;
		this.createTables = dataFromResources.getTables();
		this.listFirstName = dataFromResources.getFirstNames();
		this.listLastName = dataFromResources.getLastNames();
		this.listCourse = dataFromResources.getCourses();
	}
	
	public List<String> getListRequest() {
		
		if(createTables.isEmpty()||listFirstName.isEmpty()||listLastName.isEmpty()||listCourse.isEmpty()) {
			return Collections.emptyList();
		}
				
		listRequest = new ArrayList<>();
		listRequest.add(dataFromResources.getTables());
		listRequest.add(getCoursesSQL());		
		listRequest.add(getGroupsSQL());
		randomGroup();
		listRequest.add(getStudentsSQL());
		listRequest.add(getStudCourSQL());
		return listRequest;
	}

	private String getStudentsSQL() {
		Integer lenghtFirst = listFirstName.size();
		Integer lenghtLast = listLastName.size();
		return Stream
				.generate(() -> "('" + listFirstName.get(ran.nextInt(lenghtFirst)) + "','"
											+ listLastName.get(ran.nextInt(lenghtLast)) + "',"
											+ listForTable.get(countPlus())
											+ ")")
				.limit(200)
				.collect(Collectors.joining(",",
						"INSERT INTO students(first_name, last_name, group_id) VALUES",
						";"));

	}

	private String getCoursesSQL() {

		return listCourse.stream()
				.map(s -> "('" + s.substring(0, s.indexOf("_")) + "','"
									+ s.substring(s.indexOf("_") + 1) + "')")
				.collect(Collectors.joining(",",
							"INSERT INTO courses(course_name, course_description) VALUES ",
							";"));
	}

	private String getGroupsSQL() {
		return Stream.generate(() -> "('" + (char) (ran.nextInt(26) + 97)
													 + (char) (ran.nextInt(26) + 97) + "-"
													 + String.format("%02d", ran.nextInt(100)) + "')")
				.limit(startCountGroup)
				.collect(Collectors
						.joining(",", "INSERT INTO groups(group_name) VALUES ", ",('non');"));
	}

	private String getStudCourSQL() {
		StringBuilder builder = new StringBuilder();
		builder.append("INSERT INTO students_courses(student_id,course_id) VALUES ");
		int countCourses = 0;
		int indexCourse = 0;
		List<Integer> listIdCourse;
		for (int i = 1; i <= 200; i++) {// id student
			countCourses = ran.nextInt(3) + 1;
			listIdCourse = new ArrayList<>();
			 Stream.iterate(1, x->x+1).limit(listCourse.size()).forEach(listIdCourse::add);
			for (int j = 1; j <= countCourses; j++) {// count course
				indexCourse = ran.nextInt(listIdCourse.size());
				builder.append("(" + i + "," + listIdCourse.get(indexCourse) + "),");// id course ;
				listIdCourse.remove(indexCourse);
			}
		}
		builder.deleteCharAt(builder.length() - 1);

		builder.append(";");

		return builder.toString();
	}

	private void randomGroup() {
		List<Integer> listGroup = new ArrayList<>();
		Stream.iterate(1, x->x+1).limit(startCountGroup).forEach(listGroup::add);
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
				result.add(startCountGroup+1);
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
