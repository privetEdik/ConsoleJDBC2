package kettlebell.jdbcconsole.repository.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import kettlebell.jdbcconsole.exception.HandlerException;


public class DataFromResources {
	
	private String tables;
	private List<String> firstNames;
	private List<String> lastNames;
	private List<String> courses;
	
	public DataFromResources() {
		this.tables = getCreateTable("/createTable.log");
		this.firstNames = getListData("/firstName.log");
		this.lastNames = getListData("/lastName.log");
		this.courses = getListData("/courseName.log");
	}
			
	public String getTables() {
		return tables;
	}

	public List<String> getFirstNames() {
		return firstNames;
	}

	public List<String> getLastNames() {
		return lastNames;
	}

	public List<String> getCourses() {
		return courses;
	}

	private List<String> getListData(String fileName){
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(
				this.getClass().getResourceAsStream(fileName))))
		{
			return reader.lines()
					.collect(Collectors.toList());
		}catch (IOException e) {
			new HandlerException(e).printError();
		}		
		return Collections.emptyList();
	}
	
	private String getCreateTable(String fileName){
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(
				this.getClass().getResourceAsStream(fileName))))
		{
			return reader.lines()
					.collect(Collectors.joining());
		}catch (IOException e) {
			new HandlerException(e).printError();
		}
		return "";
	}
}
