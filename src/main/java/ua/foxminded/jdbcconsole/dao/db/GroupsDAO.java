package ua.foxminded.jdbcconsole.dao.db;

import java.util.List;

import ua.foxminded.jdbcconsole.model.Group;

public interface GroupsDAO extends ItemDAO<Group>{

	List<Group> getGroupsAndStudentsCount();
}
