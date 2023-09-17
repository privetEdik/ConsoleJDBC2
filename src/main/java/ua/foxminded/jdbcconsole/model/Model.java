package ua.foxminded.jdbcconsole.model;

public class Model {
	private int id;
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Model(int id) {
		this.id = id;
	}
	
	public Model() {

	}
	
	public Model(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
