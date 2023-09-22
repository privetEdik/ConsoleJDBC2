package kettlebell.jdbcconsole.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import kettlebell.jdbcconsole.controller.inter.GroupsModel;
import kettlebell.jdbcconsole.model.Group;
import kettlebell.jdbcconsole.repository.db.impl.GroupsRepository;

public class ControllerGroups implements GroupsModel {
	
	private GroupsRepository groupsRepository;
	
	public ControllerGroups(GroupsRepository groupsRepository) {
		this.groupsRepository = groupsRepository;
	}

	@Override
	public int add(String groupName) {
		Group group = new Group();
		group.setName(groupName);
		return groupsRepository.add(group);		
	}

	@Override
	public String getAll() {
		List<Group> list = new ArrayList<>();
		list.addAll(groupsRepository.getAll());
		if (list.isEmpty()) {
			return "";
		} else
			return list.stream()
									.map(g->String.format("|id=%-3d| name group: %-5s",g.getId(),g.getName()))
									.collect(Collectors.joining("\n"));		
	}
	
	@Override
	public String getById(int id) {
		Optional<Group> opt = groupsRepository.getById(id);
		if(opt.isPresent()) {
			Group group = opt.get();
			return String.format("|id=%-3d| name group: %-5s",group.getId(),group.getName());
		}else return "";
	}

	@Override
	public int update(String groupName, int id) {
		Group group = new Group();
		group.setName(groupName);
		group.setId(id);
		return groupsRepository.update(group);
	}

	@Override
	public int remove(int id) {
		return groupsRepository.remove(id);
	}

	@Override
	public String getGroupsAndStudentsCount() {
		Map<Integer,String> map = new HashMap<>();
		for(Group g:groupsRepository.getGroupsAndStudentsCount()) {
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
