package ua.foxminded.jdbcconsole.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ua.foxminded.jdbcconsole.controller.inter.GroupsModel;
import ua.foxminded.jdbcconsole.model.Group;
import ua.foxminded.jdbcconsole.repository.db.impl.GroupsRepo;

public class ControllerGroups implements GroupsModel {
	private Group group;
	private GroupsRepo groupsRepo;
	
	public ControllerGroups(Group group, GroupsRepo groupsRepo) {
		this.group = group;
		this.groupsRepo = groupsRepo;
	}

	@Override
	public int add(String groupName) {
		group.setName(groupName);
		return groupsRepo.add(group);		
	}

	@Override
	public String getAll() {
		List<Group> list = new ArrayList<>();
		list.addAll(groupsRepo.getAll());
		if (list.isEmpty()) {
			return "";
		} else
			return list.stream()
									.map(g->String.format("|id=%-3d| name group: %-5s",g.getId(),g.getName()))
									.collect(Collectors.joining("\n"));		
	}
	
	@Override
	public String getById(int id) {
		group = groupsRepo.getById(id);
		if(group != null) {
			return String.format("|id=%-3d| name group: %-5s",group.getId(),group.getName());
		}else return "";
	}

	@Override
	public int update(String groupName, int id) {
		group.setName(groupName);
		group.setId(id);
		return groupsRepo.update(group);
	}

	@Override
	public int remove(int id) {
		return groupsRepo.remove(id);
	}

	@Override
	public String getGroupsAndStudentsCount() {
		Map<Integer,String> map = new HashMap<>();
		for(Group g:groupsRepo.getGroupsAndStudentsCount()) {
			int key = g.getNumberOfStudents();
			String str = g.getName();
			if(map.containsKey(key)) {
				map.put(key, map.get(key)+ " " +str);
			}else {
				map.put(key, str);
			}			
		}
		if(map.containsKey(0)) {
			return "groups without students: "+ map.get(0);
		}else {
			String str = map.entrySet().stream()
					.filter(o->o.getValue().length()>5)
					.map(o->o.getValue()+": "+o.getKey())
					.collect(Collectors.joining("\n"));
			if(str.equals("")) {
				return "no groups less and equals student count";
			}else return str;
					
		}

	}

}
