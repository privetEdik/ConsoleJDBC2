package ua.foxminded.jdbcconsole.dao.db;

import java.util.List;

import ua.foxminded.jdbcconsole.model.Model;

public interface ItemDAO<T extends Model> {
	public int add(T t);
	public T getById(int id);
	public List<T> getAll();
	public int update(T t);
	public int remove(int id);
	
}
