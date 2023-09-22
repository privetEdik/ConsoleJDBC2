package kettlebell.jdbcconsole.dao.db;

import java.util.List;
import java.util.Optional;

import kettlebell.jdbcconsole.model.Model;

public interface ItemDAO<T extends Model> {
	public int add(T t);
	public Optional<T> getById(int id);
	public List<T> getAll();
	public int update(T t);
	public int remove(int id);
	
}
