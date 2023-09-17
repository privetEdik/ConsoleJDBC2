package ua.foxminded.jdbcconsole.controller.inter;

public interface GroupsModel extends ItemModel{
	int add (String groupName);
	
	//String getAll();
	
	//String getById(int Id);
	
	int update(String groupName,int id);
	String getGroupsAndStudentsCount();
	
	//void remove(int id);
}
