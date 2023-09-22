package kettlebell.jdbcconsole.dao.db;

import java.util.List;

import kettlebell.jdbcconsole.model.Group;

public interface GroupsDAO extends ItemDAO<Group>{

	List<Group> getGroupsAndStudentsCount();
}
